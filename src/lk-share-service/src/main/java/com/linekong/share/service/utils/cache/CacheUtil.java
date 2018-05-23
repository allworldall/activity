package com.linekong.share.service.utils.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.linekong.game.activity.dao.cache.GuavaCacheDao;
import com.linekong.game.activity.pojo.PO.ItemInfoPO;
import com.linekong.game.activity.utils.log.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class CacheUtil {
    @Autowired
    private CacheDao cacheDao;

    private static CacheUtil guavaUtil;

    @PostConstruct
    public void init(){
        guavaUtil = this;
        guavaUtil.guavaCacheDao = this.guavaCacheDao;
    }

    /**
     * 查询参数配置
     * @param propertyKey   参数key
     * @return  参数值
     */
    public static String getConfigValue(String propertyKey){
        String key = propertyKey;
        try {
            return getConfigValue.get(key).toString();
        } catch (Exception e) {
            LoggerUtil.error(CacheUtil.class, "param="+propertyKey+",error info:"+e.toString());
        }
        return null;
    }
    //查询短信验证码服务商的url缓存
    private static LoadingCache<String, Object> getConfigValue = CacheBuilder.newBuilder().maximumSize(1000L)
            .expireAfterWrite(120, TimeUnit.SECONDS)//在120秒没有访问或者覆盖则移除缓存
            .refreshAfterWrite(60, TimeUnit.SECONDS).build(new CacheLoader<String, Object>() {
                @Override
                public Object load(String key) throws Exception {
                    return guavaUtil.guavaCacheDao.getConfigValue(key);
                }
            });


    /**
     * 根据活动key去配置表查活动Id并与物品表进行内连接，查询出物品信息
     * @param activityType
     * @return
     */
    public static List<ItemInfoPO> getItemInfo(String activityType) {
        String key = activityType;
        try {
            return (List<ItemInfoPO>) getItemInfo.get(key);
        } catch (ExecutionException e) {
            LoggerUtil.error(CacheUtil.class, "param="+activityType+",error info:"+e.toString());
        }
        return null;
    }
    //根据活动key查询物品缓存
    private static LoadingCache<String, Object> getItemInfo = CacheBuilder.newBuilder().maximumSize(1000L)
            .expireAfterWrite(120, TimeUnit.SECONDS)//在120秒没有访问或者覆盖则移除缓存
            .refreshAfterWrite(60, TimeUnit.SECONDS).build(new CacheLoader<String, Object>() {

                @Override
                public List<ItemInfoPO> load(String key) throws Exception {
                    return guavaUtil.guavaCacheDao.getItemInfo(key);
                }
            });
}
