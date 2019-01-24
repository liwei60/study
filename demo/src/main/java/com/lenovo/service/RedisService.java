package com.lenovo.service;



import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    @Qualifier("stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    public void set(String key,Object object){
        redisTemplate.opsForValue().set(key,JSONObject.toJSONString(object));
    }

    public String get(String key){
        return redisTemplate.opsForValue().get(key);
    }
    public void rightPush(String listName,Object object){
        redisTemplate.opsForList().rightPush(listName, JSONObject.toJSONString(object));

    }

    public void leftPush(String listName,Object object){
        redisTemplate.opsForList().leftPush(listName,JSONObject.toJSONString(object));
    }

    public Object rightPop(String listName){
        return redisTemplate.opsForList().rightPop(listName,1L, TimeUnit.HOURS);
        //return redisTemplate.opsForList().rightPop(listName);
    }

    public Object rpoplpush(String taskQueue, String tmpQueue){
        Object o = redisTemplate.opsForList().rightPopAndLeftPush(taskQueue,tmpQueue,1L,TimeUnit.HOURS);
        //Object o = redisTemplate.opsForList().rightPopAndLeftPush(taskQueue,tmpQueue);
        return o;
    }

    public Object leftpop(String listName){
        return redisTemplate.opsForList().leftPop(listName,1L,TimeUnit.HOURS);
        //return redisTemplate.opsForList().leftPop(listName);
    }
}
