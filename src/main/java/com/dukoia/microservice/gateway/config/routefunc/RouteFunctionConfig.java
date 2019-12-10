package com.dukoia.microservice.gateway.config.routefunc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @Author JefferyChang
 * @Date 2019/5/17 17 44
 * @Desp  路由断言
 */
@Configuration
public class RouteFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> responseRouterFunction(){
        RouterFunction<ServerResponse> route = RouterFunctions.route(
                RequestPredicates.path("/test"),
                request -> ServerResponse.ok().body(BodyInserters.fromObject("Hello Gateway")));
        return route;
    }


}
