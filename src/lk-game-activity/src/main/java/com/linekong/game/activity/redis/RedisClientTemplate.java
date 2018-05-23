package com.linekong.game.activity.redis;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.linekong.game.activity.utils.Common;
import com.linekong.game.activity.utils.SysCodeConstant;
import com.linekong.game.activity.utils.log.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * 成员变量加泛型前后部分代码被改动，如需恢复到加泛型的状态，请从SVN中恢复，加泛型的SVN版本为82747
 */
@Component
public class RedisClientTemplate {
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 通过可变参数批量传入key然后进行删除
	 * @param String ... keys
	 */
	public void remove(final String ... keys) {
		for(String key : keys) {
			remove(key);
		}
	}
	/**
	 * 通过key匹配的方式来进行key值的批量删除
	 * @param String pattern
	 */
	@SuppressWarnings("unchecked")
	public void removePattern(final String pattern) {
		/*Set<Serializable> keys = this.redisTemplate.keys(pattern);
		if(keys.size() > 0) {
			this.redisTemplate.delete(keys);
		}*/
		Set<String> keys = this.redisTemplate.keys(pattern);
		if(keys.size() > 0) {
			this.redisTemplate.delete(keys);
		}
	}
	/**
	 * 删除对应的key值
	 * @param String key
	 */
	@SuppressWarnings("unchecked")
	public void remove(final String key) {
		try{
			if(exists(key)) {
				this.redisTemplate.delete(key);
			}
		}catch(Exception e){
			LoggerUtil.error(this.getClass(), "error info:" + e.toString());
		}

	}
	/**
	 * 判断key值是否存在
	 * @param String key
	 * @return boolean 存在返回true,不存在返回false
	 */
	@SuppressWarnings("unchecked")
	public boolean exists(final String key) {
		return this.redisTemplate.hasKey(key);
	}
	/**
	 * 读取缓存中的内容
	 * @param String key
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String get(final String key) {
		try {
			Object result = null;
			this.redisTemplate.setValueSerializer(new StringRedisSerializer());
			ValueOperations<String, String> operations = this.redisTemplate.opsForValue();
			result = operations.get(key);
			if (result == null) {
				return null;
			} else {
				return result.toString();
			}
		}catch (Exception e){
			return null;
		}
	}
	/**
	 * 写入缓存
	 * @param String key
	 * @param Object value
	 * @return boolean true 写入成功，false 写入失败
	 */
	@SuppressWarnings("unchecked")
	public boolean set(final String key, String value) {
		boolean result = false;
	    try {
		    ValueOperations<String, String> operations = this.redisTemplate.opsForValue();
		    operations.set(key, value);
		    result = true;
	    } catch (Exception e) {
	    		e.printStackTrace();
	    }
	    return result;
	}
	/**
	 * 写入缓存
	 * @param String key
	 * @param Object value
	 * @param Long expireTime
	 * @return boolean
	 *         成功返回true
	 *         失败返回false
	 */
	@SuppressWarnings("unchecked")
	public boolean set(final String key, String value, Long expireTime) {
	    boolean result = false;
	    try {
			ValueOperations<String, String> operations = this.redisTemplate.opsForValue();
			operations.set(key, value);
			this.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
	    } catch (Exception e) {
			e.printStackTrace();
	    }
	    return result;
    }
	/**
	 * hashMap值修改
	 * @param String key
	 * @param Map<String,String> value
	 * @return boolean
	 *         true 修改成功
	 *         false 修改失败
	 */
	@SuppressWarnings("unchecked")
	public boolean hmset(String key, Map<String, String> value) {
        boolean result = false;
        try {
            this.redisTemplate.opsForHash().putAll(key, value);
            result = true;
        } catch (Exception e) {
        		e.printStackTrace();
        }
        return result;
    }

	/**
	 * hashMap值修改，带过弃时间的，提供给类似保存手机号验证码60s的场景
	 * @param String key
	 * @param Map<String,String> value
	 * @param expireTime 过弃时间
	 * @return boolean
	 *         true 修改成功
	 *         false 修改失败
	 */
	@SuppressWarnings("unchecked")
	public boolean hmset(String key, Map<String, String> value, Long expireTime) {
		boolean result = false;
		try {
			this.redisTemplate.opsForHash().putAll(key, value);
			this.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
    /**
     * 获取hashMap值
     * @param String key
     *               获取对应的key值
     * @return Map<String,String> result
     */
    @SuppressWarnings("unchecked")
	public  Map<Object,Object> hmget(String key) {
        Map<Object,Object> result =null;
        try {
            result = this.redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
        e.printStackTrace();
        }
        return result;
    }

	/**
	 * 设置key的increament
	 * @param key
	 * @param increment
	 * @param expireTime
	 * @return
	 */
	public long incr(String key, long increment, long expireTime) {
		long result = 0;
		try {
			result = this.redisTemplate.opsForValue().increment(key, increment);
			this.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.error(this.getClass(), "error info:"+e.toString());
		}
		return result;
	}

	/**
	 * 将String存入redis的set数据结构中
	 * @param key
	 * @param value1
	 * @return
	 */
	public boolean setAdd(String key, String value1) {
		boolean result = false;
		try{
			SetOperations<String,String> set = this.redisTemplate.opsForSet();
			set.add(key, value1);
			result = true;
		}catch (Exception e){
			LoggerUtil.error(this.getClass(), " deal param:key="+key+"value="+value1+",error info:"+e.toString());
		}
		return result;
	}

	/**
	 * 对两个set集合取交集
	 * @return
	 */
	public Set<String> innerSet(Set<String> set, String originalKey) {
		try {
			long start = System.currentTimeMillis();
			String arr1 [] = new String[set.size()];
			String arr [] = set.toArray(arr1);
			String keyTemp = SysCodeConstant.REDIS_TEMP_KEY+ Common.createString();
			for (int i = 0;i<arr.length;i++){
				if(arr[i] == null) {
					continue;
				}
				this.setAdd(keyTemp,arr[i]);
			}
			this.redisTemplate.expire(keyTemp, 60L, TimeUnit.SECONDS);//取完交集就可以过期了
			Set<String> resultSet = this.redisTemplate.opsForSet().intersect(keyTemp, originalKey);
			//取交集时间过长打印日志,可以考虑直接返回，采用DB查询
			if(System.currentTimeMillis() - start > 60000L) {
				LoggerUtil.info(this.getClass(), "innerSet method execute time="+(System.currentTimeMillis() - start) + "mils");
			}
			//监控一下取交集是否生效
			if(resultSet != null && resultSet.size() > 0) {
				LoggerUtil.info(this.getClass(), "get innerSet size =" + resultSet.size());
			}
			return resultSet;
		}catch(Exception e) {
			LoggerUtil.error(this.getClass(), " deal param:set=" + set + ",originalKey=" + originalKey + ",error info:" +e.toString());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查找set集合中有无目标数据
	 * @return
	 */
	public boolean isMember(String key, String targetValue) {
		try{
			return this.redisTemplate.opsForSet().isMember(key, targetValue);
		}catch (Exception e){
			LoggerUtil.error(this.getClass(), " deal param:key=" + key + ",targetValue=" + targetValue + ",error info:"+e.toString());
			return false;
		}
	}
}
