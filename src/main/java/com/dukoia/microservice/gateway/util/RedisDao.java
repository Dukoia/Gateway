package com.dukoia.microservice.gateway.util;

import java.util.concurrent.TimeUnit;

/**
 * @Author itw_changgl
 * @Date 2019/3/25 16 54
 * @Desp  redis接口操作
 */
public interface RedisDao {

    /**
     * 根据key 设置redis value
     * @param key
     * @param value
     */
    public void setKey(String key, String value);

    /**
     * 根据key 设置value 并设置超时时长 可以限定单位
     * @param key
     * @param value
     * @param timeout
     * @param unit
     */
    public void setKey(String key, String value, long timeout, TimeUnit unit) ;

    /**
     * 根据Key设置value  设置一天的超时时长
     * @param key
     * @param value
     */
    public void setKeyOneDay(String key, String value);

    /**
     * 通过key 获取value
     * @param key
     * @return
     */
    public String getValue(String key);

    /**
     * 通过Key删除value
     * @param key
     */
    public void del(String key);

    /**
     * 判断某个键值对是否存在
     * @param key
     * @return
     */
    public boolean exists(final String key);

    /**
     * 刷新全部缓存
     */
    public void flushAll();
}
