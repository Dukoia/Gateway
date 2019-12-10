package com.dukoia.microservice.gateway.dao;


import com.dukoia.microservice.gateway.pojo.ConfigChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author:JefferyChang
 * @Date:2019/5/21 15:36
 * @Desp:
 */
@Repository
public interface ConfigChannelRepository extends JpaRepository<ConfigChannel,Integer> {

    /**
     * 根据 channnelOpenCode 查询 configChannel
     * @param channnelOpenCode
     * @return
     */
    ConfigChannel findConfigChannelByChannelOpenCode(String channnelOpenCode);


}
