package com.dsky.baas.configservice.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.dsky.baas.configservice.model.BlackListBean;
import com.dsky.baas.configservice.model.ExchangeBean;
import com.dsky.baas.configservice.model.PromoterBean;

import redis.clients.jedis.exceptions.JedisConnectionException;


public class RedisMethonds {
	private static Logger logger = Logger.getLogger(RedisMethonds.class);
	private static RedisTemplate redisTemplate;
	
	public void setRedisTemplate(RedisTemplate redisTemplate) {
	    RedisSerializer stringSerializer = new StringRedisSerializer();
	    redisTemplate.setHashKeySerializer(stringSerializer);
	    redisTemplate.setHashValueSerializer(stringSerializer);
		this.redisTemplate = redisTemplate;
	}
	
	//更新后删除redis上对应的活动配置信息的key
	
	public static void delActivitieKey(String key) {
		try{
			if(redisTemplate.hasKey(key)) {
		           redisTemplate.delete(key);
		           logger.info("调用delActivitieKey 删除 : "+key);
		     }else {
		    	 logger.info("Redis 中没有对应的 Key : "+key);
		     }
		}catch (JedisConnectionException e) {
			logger.error("redis连接出现异常 "+e.getMessage(), e);
			return;
		}
	}

	public static int hashSet(String key,String field,String value) {

		try{
			if(redisTemplate.opsForHash().hasKey(key, field)) {
				//如果redis中已经存在该键值 则进行更新
		           redisTemplate.opsForHash().delete(key, field);
		           logger.info("key已经存在 删除 : "+key+"然后进行更新");
		           redisTemplate.opsForHash().put(key, field, value);
		     }else {
		    	 //如果redis中没有这个键值 则直接存储
		    	 redisTemplate.opsForHash().put(key, field, value);
		    	 logger.info("Redis 中没有对应的 Key : "+key+"   filed="+field);
		     }
			return 1;
		}catch (JedisConnectionException e) {
			logger.error("redis连接出现异常 "+e.getMessage(), e);
			return 0;
		}
	}
	
	public static void delHashField(String key,String field) {

        if(field != null )
		try{
			if(redisTemplate.opsForHash().hasKey(key, field)) {
				redisTemplate.opsForHash().delete(key, field);
		           logger.info("调用delActivitieKey 删除 : "+key);
		     }else {
		    	 logger.info("Redis 中没有对应的 Key : "+key);
		     }
		}catch (JedisConnectionException e) {
			logger.error("redis连接出现异常 "+e.getMessage(), e);
			return;
		}
	}
	
	
	
	public static int set(String key,String value) {
		try{
			if(redisTemplate.hasKey(key)) {
				//如果redis中已经存在该键值 则进行更新
		           redisTemplate.delete(key);
		           logger.info("key已经存在 删除 : "+key+"然后进行更新");
		           redisTemplate.opsForValue().set(key, value);
		     }else {
		    	 //如果redis中没有这个键值 则直接存储
		    	 redisTemplate.opsForValue().set(key, value);
		    	 logger.info("Redis 中没有对应的 Key : "+key);
		     }
			return 1;
		}catch (JedisConnectionException e) {
			logger.error("redis连接出现异常 "+e.getMessage(), e);
			return 0;
		}
	}
	
	public static int set(String key,PromoterBean value) {
		try{
			if(redisTemplate.hasKey(key)) {
				//如果redis中已经存在该键值 则进行更新
		           redisTemplate.delete(key);
		           logger.info("key已经存在 删除 : "+key+"然后进行更新");
		           redisTemplate.opsForValue().set(key, value);
		     }else {
		    	 //如果redis中没有这个键值 则直接存储
		    	 redisTemplate.opsForValue().set(key, value);
		    	 logger.info("Redis 中没有对应的 Key : "+key);
		     }
			return 1;
		}catch (JedisConnectionException e) {
			logger.error("redis连接出现异常 "+e.getMessage(), e);
			return 0;
		}
	}
	
	public static int set(String key,ExchangeBean value) {
		try{
			if(redisTemplate.hasKey(key)) {
				//如果redis中已经存在该键值 则进行更新
		           redisTemplate.delete(key);
		           logger.info("key已经存在 删除 : "+key+"然后进行更新");
		           redisTemplate.opsForValue().set(key, value);
		     }else {
		    	 //如果redis中没有这个键值 则直接存储
		    	 redisTemplate.opsForValue().set(key, value);
		    	 logger.info("Redis 中没有对应的 Key : "+key);
		     }
			return 1;
		}catch (JedisConnectionException e) {
			logger.error("redis连接出现异常 "+e.getMessage(), e);
			return 0;
		}
	}
	
	public static int setBlackList(String key,String value,int expireTime) {
		try{
			if(redisTemplate.hasKey(key)) {
				//如果redis中已经存在该键值 则进行更新
		           redisTemplate.delete(key);
		           logger.info("key已经存在 删除 : "+key+"然后进行更新");
		           redisTemplate.opsForValue().set(key, value);
		           redisTemplate.expire(key, expireTime,TimeUnit.SECONDS);//设置过期时间
		     }else {
		    	 //如果redis中没有这个键值 则直接存储
		    	 redisTemplate.opsForValue().set(key, value);
		    	 redisTemplate.expire(key, expireTime,TimeUnit.SECONDS);//设置过期时间
		    	 logger.info("Redis 中没有对应的 Key : "+key);
		     }
			return 1;
		}catch (JedisConnectionException e) {
			logger.error("redis连接出现异常 "+e.getMessage(), e);
			return 0;
		}
	}
	
	public static String get(String key) {
		try{
			if(redisTemplate.hasKey(key)) {
				//如果redis中已经存在该键值 则进行更新
				   logger.info("开始获取key : "+key);
		           String value = redisTemplate.opsForValue().get(key).toString();
		           logger.info("redis返回 key="+key+" 的值为 value="+value);
		           return value;
		     }else {
		    	 //如果redis中没有这个键值 则直接返回空
		    	 redisTemplate.opsForValue().set(key, "1");//如果当前redis中没有该key的记录，则加入默认为紧急测试关闭的key
		    	 logger.info("Redis get(String key) 中没有对应的 Key : "+key);
		    	 return "1";
		     }
		}catch (JedisConnectionException e) {
			logger.error("redis连接出现异常 "+e.getMessage(), e);
			return null;
		}
	}
	
	public static PromoterBean getPromoterBean(String key) {
		try{
			if(redisTemplate.hasKey(key)) {
				//如果redis中已经存在该键值 则进行更新
				   logger.info("开始获取key : "+key);
		           PromoterBean value = (PromoterBean) redisTemplate.opsForValue().get(key);
		           logger.info("redis返回 key="+key+" 的值为 value="+value.toString());
		           return value;
		     }else {
		    	 //如果redis中没有这个键值 则直接返回空
		    	 //redisTemplate.opsForValue().set(key, "1");//如果当前redis中没有该key的记录，则加入默认为紧急测试关闭的key
		    	 logger.info("Redis get(String key) 中没有对应的 Key : "+key);
		    	 return null;
		     }
		}catch (JedisConnectionException e) {
			logger.error("redis连接出现异常 "+e.getMessage(), e);
			return null;
		}
	}
	
	public static ExchangeBean getExchangeBean(String key) {
		try{
			if(redisTemplate.hasKey(key)) {
				//如果redis中已经存在该键值 则进行更新
				   logger.info("开始获取key : "+key);
		           ExchangeBean value = (ExchangeBean) redisTemplate.opsForValue().get(key);
		           logger.info("redis返回 key="+key+" 的值为 value="+value.toString());
		           return value;
		     }else {
		    	 //如果redis中没有这个键值 则直接返回空
		    	 //redisTemplate.opsForValue().set(key, "1");//如果当前redis中没有该key的记录，则加入默认为紧急测试关闭的key
		    	 logger.info("Redis get(String key) 中没有对应的 Key : "+key);
		    	 return null;
		     }
		}catch (JedisConnectionException e) {
			logger.error("redis连接出现异常 "+e.getMessage(), e);
			return null;
		}
	}
	
	public static int setBlackList(String key,List<BlackListBean> list) {
		try{
			if(redisTemplate.hasKey(key)) {
				//如果redis中已经存在该键值 则进行更新
		           redisTemplate.delete(key);
		           logger.info("key已经存在 删除 : "+key+"然后进行更新");
		           redisTemplate.opsForValue().set(key, list);
		     }else {
		    	 //如果redis中没有这个键值 则直接存储
		    	 redisTemplate.opsForValue().set(key, list);
		    	 logger.info("Redis 中没有对应的 Key : "+key);
		     }
			return 1;
		}catch (JedisConnectionException e) {
			logger.error("redis连接出现异常 "+e.getMessage(), e);
			return 0;
		}
	}
	
	public static List<BlackListBean> getBlackListBean(String key) {
		try{
			if(redisTemplate.hasKey(key)) {
				//如果redis中已经存在该键值 则进行更新
				   logger.info("开始获取key : "+key);
				   List<BlackListBean> list = (List<BlackListBean>) redisTemplate.opsForValue().get(key);
		           logger.info("redis返回 key="+key+" 值size大小 ="+list.size());
		           return list;
		     }else {
		    	 //如果redis中没有这个键值 则直接返回空
		    	 //redisTemplate.opsForValue().set(key, "1");//如果当前redis中没有该key的记录，则加入默认为紧急测试关闭的key
		    	 logger.info("Redis get(String key) 中没有对应的 Key : "+key);
		    	 return null;
		     }
		}catch (JedisConnectionException e) {
			logger.error("redis连接出现异常 "+e.getMessage(), e);
			return null;
		}
	}
}
