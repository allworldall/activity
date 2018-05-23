package com.linekong.share.service.utils.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;

import com.linekong.share.service.utils.SysCodeConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.linekong.share.service.dao.model.QueryConfigInfoDao;
import com.linekong.share.service.utils.log.LoggerUtil;

@Component
public class CommonCache {

	@Autowired
	private QueryConfigInfoDao queryConfigInfoDao;

	private static CommonCache commonCache;

	@PostConstruct
	public void init() {
		commonCache = this;
		commonCache.queryConfigInfoDao = this.queryConfigInfoDao;
	}

	// 查询通行证配置信息表中的数据
	private static LoadingCache<String, Object> configCache = CacheBuilder.newBuilder().maximumSize(1000)
			.expireAfterWrite(120, TimeUnit.SECONDS) // 在120秒没有被写访问或者覆盖则移除缓存
			.refreshAfterWrite(60, TimeUnit.SECONDS).build(new CacheLoader<String, Object>() {
				@Override
				public Object load(String key) throws Exception {
					 return  commonCache.queryConfigInfoDao.queryConfigInfo(key) == null ? "none" : commonCache.queryConfigInfoDao.queryConfigInfo(key);

				}
			});

	/**
	 * 获取配置信息表中的配置信息
	 * 
	 * @param String
	 *            key 缓存key值
	 */
	public static Object getConfigInfo(final String key) {
		try {
			return configCache.get(key);
		} catch (ExecutionException e) {
			LoggerUtil.error(CommonCache.class, "get guava chache error:" + e.getMessage());
		}
		return null;
	}
	/**
	 *
	 * 获取赠送物品需要发送的地址
	 * @param String key
	 *             key 缓存key值
     * @return Object
	 */
	public static Object getSendItemAddress(final String key){
		try {
			Object address = configCache.get(key);
			if("none".equals(address)){
				address = configCache.get(SysCodeConstant.INVOKE_XML_RPC_ADDRESS);
			}
			return address;
		}catch (Exception e){
			LoggerUtil.error(CommonCache.class, "get guava chache error:" + e.getMessage());
		}
		return null;

	}
}
