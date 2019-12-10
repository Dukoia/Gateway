package com.dukoia.microservice.gateway.dao;

import com.dukoia.microservice.gateway.pojo.RedisMonitorVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author JefferyChang
 * @Date 2019/5/24 18 02
 * @Desp
 */
@Repository
public interface RedisMonitorRepository extends JpaRepository<RedisMonitorVo,Integer> {

}
