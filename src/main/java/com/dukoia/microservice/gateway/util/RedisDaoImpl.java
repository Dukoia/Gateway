package com.dukoia.microservice.gateway.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author itw_changgl
 * @Date 2019/3/25 16 54
 * @Desp
 */
@Component
public class RedisDaoImpl  implements  RedisDao{

    @Autowired
    private StringRedisTemplate template;

    @Override
   public void setKey(String key, String value) {
        ValueOperations<String, String> ops = template.opsForValue();
        ops.set(key, value, 30, TimeUnit.DAYS);
    }

    @Override
    public void setKeyOneDay(String key, String value) {
        ValueOperations<String, String> ops = template.opsForValue();
        ops.set(key, value, 24, TimeUnit.HOURS);
    }

    @Override
    public void setKey(String key, String value, long timeout, TimeUnit unit) {
        ValueOperations<String, String> ops = template.opsForValue();
        ops.set(key, value, timeout, unit);
    }

    @Override
    public String getValue(String key) {
        ValueOperations<String, String> ops = this.template.opsForValue();
        return ops.get(key);
    }

    @Override
    public void del(String key) {
        Boolean isExist = template.hasKey(key);
        if(isExist){
            this.template.delete(key);
        }
    }

    @Override
    public boolean exists(final String key) {
        return template.hasKey(key);
    }

    @Override
    public void flushAll(){
        Set<String> keys = template.keys("*");
        this.template.delete(keys);
    }





}
