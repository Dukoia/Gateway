package com.dukoia.microservice.gateway.service;

import com.dukoia.microservice.gateway.pojo.ConfigChannel;

/**
 * @author:JefferyChang
 * @Date:2019/5/21 16:00
 * @Desp:
 */
public interface ConfigChannelService {


    ConfigChannel findConfigChannelByChannelOpenCode(String channnelOpenCode);

}
