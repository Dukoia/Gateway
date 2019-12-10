package com.dukoia.microservice.gateway.config.routelocat;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author JefferyChang
 * @Date 2019/5/17 17 45
 * @Desp 路由过滤
 */
@Configuration
public class RouteLocatorConfig {
    @Bean
    public RouteLocator routerLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/index/**").uri("lb://hp-web"))
                .route(r -> r.path("/health/**").uri("lb://hp-web"))
                .build();
    }


}
