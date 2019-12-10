package com.dukoia.microservice.gateway.filters.bak;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author:JefferyChang
 * @Date:2019/5/13 15:33
 * @Desp:
 */
@Component
@Slf4j
public class RemoteAddrKeyResolver implements KeyResolver {

    private static final String ELAPSED_TIME_BEGIN = "elapsedTimeBegin";
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        //Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress())
        Object obj = exchange.getAttribute(ELAPSED_TIME_BEGIN);
        String str = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress() + ":" + obj.toString();
        log.info("【RemoteAddrKeyResolver:{}】",str);
        return Mono.just(str) ;
    }
}
