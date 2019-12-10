package com.dukoia.microservice.gateway.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author JefferyChang
 * @Date 2019/5/24 17 57
 * @Desp
 */
@Data
@Entity
@Table(name = "redis_monitor")
public class RedisMonitorVo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "rps")
    private Integer rps;
    @Column(name = "rate_count")
    private Integer rateCount;
    @Column(name = "start_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy年MM月dd日HH:mm:ss")
    private Date startTime;
    @Column(name = "end_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy年MM月dd日HH:mm:ss")
    private Date endTime;

    public RedisMonitorVo(Integer rateCount, Date startTime, Date endTime) {
        this.rps = rateCount/60;
        this.rateCount = rateCount;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
