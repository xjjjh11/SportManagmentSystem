package com.sms.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * @author Jared
 * @date 2021/6/16 10:20
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void set(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public void setex(String key, String value, Long seconds) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, seconds);
    }

    public void size(String s){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        System.out.println("valueOperations.size:"+valueOperations.size(s));
    }
}
