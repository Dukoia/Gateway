package com.dukoia.microservice.gateway.filters.bak;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author:JefferyChang
 * @Date:2019/5/13 15:20
 * @Desp:   监控服务调用时长
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static final String ELAPSED_TIME_BEGIN = "elapsedTimeBegin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(ELAPSED_TIME_BEGIN, System.currentTimeMillis());
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    Long startTime = exchange.getAttribute(ELAPSED_TIME_BEGIN);
                    if (startTime != null) {
                        log.info(exchange.getRequest().getURI().getRawPath() + ": " + (System.currentTimeMillis() - startTime) + "ms");
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        //Ordered.LOWEST_PRECEDENCE：2147483647
      //  log.info("【>>>>>>>Ordered.LOWEST_PRECEDENCE：{}】",Ordered.LOWEST_PRECEDENCE);
        return Ordered.LOWEST_PRECEDENCE;
    }
}
