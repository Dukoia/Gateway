package com.dukoia.microservice.gateway.config.crossConfig;

import cn.hutool.core.util.IdUtil;
import com.dukoia.microservice.gateway.common.ConstantField;
import com.dukoia.microservice.gateway.filters.limitrate.RedisMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @author Dukoia
 * @createTime 2019/12/10 10:50
 */
@Configuration
public class CrosFilterConfig {

    @Autowired
    private RedisMonitor redisMonitor;

    @Bean
    public WebFilter crosFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            HttpHeaders requestHeaders = request.getHeaders();
            ServerHttpRequest traceId = request.mutate().header(ConstantField.TRACE_ID, IdUtil.simpleUUID()).build();
            ServerWebExchange serverWebExchange = ctx.mutate().request(traceId).build();
            if (!CorsUtils.isCorsRequest(request)) {
                redisMonitor.countRate(serverWebExchange.getRequest().getHeaders().get(ConstantField.TRACE_ID).get(0),new Date());
                return chain.filter(serverWebExchange);
            }
            ServerHttpResponse response = ctx.getResponse();
            HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
            HttpHeaders headers = response.getHeaders();
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
            headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
            if (requestMethod != null) {
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
            }
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "all");
            headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }
            return chain.filter(ctx);
        };
    }

}
