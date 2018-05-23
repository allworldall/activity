package com.linekong.share.service.dao.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.linekong.share.service.utils.DataFormatUtils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linekong.share.service.pojo.form.RecordUserShareInfoForm;
import com.linekong.share.service.pojo.form.ClickShareInfoRecordForm;
import com.linekong.share.service.redis.RedisClientTemplate;
import com.linekong.share.service.utils.SysCodeConstant;
import com.linekong.share.service.utils.cache.CommonCache;
import com.linekong.share.service.utils.exception.RedisException;

@Repository
public class RecordShareInfoCache {
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	/**
	 * 在缓存中记录不重复用户的点击
	 * @throws RedisException 
	 */
	public int recordShareClickInfo(ClickShareInfoRecordForm form) throws RedisException{
		try {
			//记录点击到redis
			String shareClickLogKey = SysCodeConstant.REDIS_KEY_SHARE_LOG+form.getUniqulyId()+ DateUtil.getTimeString2(new Date());
			long upperLimit = Long.parseLong(CommonCache.getConfigInfo(SysCodeConstant.SHARE_UPPER_LIMIT).toString());
			if(redisClientTemplate.getSet(shareClickLogKey) >= upperLimit) {//已经满足不进行记录
				return SysCodeConstant.MET_CONDITION;
			}else {//进行记录
				long result = redisClientTemplate.addSet(shareClickLogKey, form.getClickIp()+"_"+form.getClickUa());
				redisClientTemplate.setExpire(shareClickLogKey, Long.parseLong(CommonCache.getConfigInfo(SysCodeConstant.REDIS_KEY_EXPIRE_TIME).toString()));
				if(result > 0) {
					/*if(redisClientTemplate.getSet(shareClickLogKey) == upperLimit) {
						return SysCodeConstant.SUCCESS;
					}else {
						return SysCodeConstant.SUCCESS;
					}*/
					return SysCodeConstant.SUCCESS;
				}else {
					return SysCodeConstant.ALREADY_CLICK;
				}
			}
		} catch (Exception e) {
			throw new RedisException("redis connection error:"+e.getMessage());
		}
	}
	/**
	 * 将分享用户信息记录到缓存中
	 */
	public int recordShareUserInfo(RecordUserShareInfoForm form) throws RedisException{
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put(SysCodeConstant.SHARE_USER_INFO_GAMEID, form.getGameId());
			map.put(SysCodeConstant.SHARE_USER_INFO_GATEWAYID, form.getGatewayId());
			map.put(SysCodeConstant.SHARE_USER_INFO_PASSPORTNAME, form.getPassportName());
			map.put(SysCodeConstant.SHARE_USER_INFO_UNIQULYID, form.getUniqulyId());
			map.put(SysCodeConstant.SHARE_USER_INFO_ACTIVITYID, form.getActivityId());
			String key = SysCodeConstant.REDIS_KEY_SHARE_USER_LOG + form.getUniqulyId();
			redisClientTemplate.hmset(key, map);
			redisClientTemplate.setExpire(key, Long.parseLong(CommonCache.getConfigInfo(SysCodeConstant.REDIS_KEY_EXPIRE_TIME).toString()));
			return SysCodeConstant.SUCCESS;
		} catch (Exception e) {
			throw new RedisException("redis connection error:"+e.getMessage());
		}
		
	}
	/**
	 * 判断key 是否存在
	 */
	public boolean existKey(String key) throws RedisException{
		try {
			return redisClientTemplate.exists(key);
		} catch (Exception e) {
			throw new RedisException("redis connection error:"+e.getMessage());
		}
	}
	/**
	 * 删除
	 */
	public void delKey(String key) throws RedisException{
		try {
			redisClientTemplate.remove(key);
		} catch (Exception e) {
			throw new RedisException("redis connection error:"+e.getMessage());
		}
	}

}
