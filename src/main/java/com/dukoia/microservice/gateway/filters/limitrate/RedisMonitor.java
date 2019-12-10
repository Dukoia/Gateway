package com.dukoia.microservice.gateway.filters.limitrate;

import cn.hutool.core.date.DateUtil;
import com.dukoia.microservice.gateway.dao.RedisMonitorRepository;
import com.dukoia.microservice.gateway.pojo.RedisMonitorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author JefferyChang
 * @Date 2019/5/24 16 20
 * @Desp
 */
@Component
@Configurable
@EnableScheduling
@Slf4j
public class RedisMonitor {

    private static Integer count = 0;

    private  Date lastDate = new Date();

    private  Date currentTime = new Date();

    @Autowired
    private RedisMonitorRepository redisMonitorRepository;

    public  void countRate(String traceId, Date currentTime) {
        this.currentTime = currentTime;
        int millsecond = DateUtil.millsecond(currentTime);
        if (0<=millsecond&& millsecond<=999){
            count++;
        }
    }

    @Scheduled(cron = "0/60 * * * * ?")
    public void setCountToNull(){
        redisMonitorRepository.save(new RedisMonitorVo(count,lastDate,currentTime));
        lastDate=new Date();
        count=0;
    }





}
