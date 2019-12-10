package com.dukoia.microservice.gateway.service.impl;

import com.dukoia.microservice.gateway.dao.ConfigChannelRepository;
import com.dukoia.microservice.gateway.pojo.ConfigChannel;
import com.dukoia.microservice.gateway.service.ConfigChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:JefferyChang
 * @Date:2019/5/21 16:01
 * @Desp:
 */
@Service
public class ConfigChannelServiceImpl implements ConfigChannelService {

    @Autowired
    private ConfigChannelRepository configChannelRepository;

    @Override
    public ConfigChannel findConfigChannelByChannelOpenCode(String channnelOpenCode) {
        return configChannelRepository.findConfigChannelByChannelOpenCode(channnelOpenCode);
    }
}
