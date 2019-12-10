package com.dukoia.microservice.gateway;

import com.dukoia.microservice.gateway.dao.ConfigChannelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayApplicationTests {

    @Autowired
    ConfigChannelRepository configChannelRepository;

    @Test
    public void contextLoads() {
        long count = configChannelRepository.count();
        System.out.println(count);
    }

}
