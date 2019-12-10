package com.dukoia.microservice.gateway.filters.bak;

import com.dukoia.microservice.gateway.common.ResultCode;
import com.dukoia.microservice.gateway.pojo.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.TreeMap;

/**
 * @author:JefferyChang
 * @Date:2019/5/13 15:53
 * @Desp:
 */
@Slf4j
@Component
public class ComplexAuthFilter  implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

//        exchange.getResponse().setStatusCode(HttpStatus.OK);
//        exchange.getResponse().getHeaders().add("Content-Type", "application/json;charset=UTF-8");
//        ResultDto result = new ResultDto();
//        //后端调用跳过验签
//        boolean skipAuth = Boolean.valueOf(exchange.getRequest().getQueryParams().getFirst("skipAuth"));
//        if (!skipAuth) {
//            String sign = exchange.getRequest().getQueryParams().getFirst("sign");
//            if (StringUtils.isEmpty(sign)) {
//                //没有验签参数
//                result.setCode(ResultCode.SGIN_EMPTY.getCode());
//                result.setMsg(ResultCode.SGIN_EMPTY.getMsg());
//                return exchange.getResponse().writeWith(Flux.just(this.getBodyBuffer(exchange.getResponse(), result)));
//            }
//            String publicKey = exchange.getRequest().getHeaders().getFirst("publicKey");
//            if (StringUtils.isEmpty(publicKey)) {
//                //没有公钥
//                result.setCode(ResultCode.PUBLICKEY_EMPTY.getCode());
//                result.setMsg(ResultCode.PUBLICKEY_EMPTY.getMsg());
//                return exchange.getResponse().writeWith(Flux.just(this.getBodyBuffer(exchange.getResponse(), result)));
//            }
//            String privateKey = redisUtil.getValueStr(publicKey);
//            if (!StringUtils.isEmpty(privateKey)) {
//                TreeMap<String, List<String>> parameterMap = new TreeMap<>(exchange.getRequest().getQueryParams());
//                //验签
//                StringBuilder sb = new StringBuilder();
//                parameterMap.entrySet().forEach(stringEntry -> {
//                    if (!StringUtils.equalsIgnoreCase(stringEntry.getKey(), "sign")) {
//                        if (!CollectionUtils.isEmpty(stringEntry.getValue())) {
//                            sb.append(stringEntry.getKey());
//                            sb.append("=");
//                            sb.append(stringEntry.getValue().stream().findFirst().get());
//                        }
//                    }
//                });
//                sb.append("privateKey=");
//                sb.append(privateKey);
//                System.out.println(sb.toString());
////                String serverSign = DigestUtils.md5Hex(sb.toString());
////                System.out.println(serverSign);
//                if (!serverSign.equals(sign)) {
//                    //验签不通过
//                    result.setCode(ResultCode.SIGN_INVALID.getCode());
//                    result.setMsg(ResultCode.SIGN_INVALID.getMsg());
//                    return exchange.getResponse().writeWith(Flux.just(this.getBodyBuffer(exchange.getResponse(), result)));
//                }
//            } else {
//                //私钥过期
//                result.setCode(ResultCode.PRIVATEKEY_EXPIRE.getCode());
//                result.setMsg(ResultCode.PRIVATEKEY_EXPIRE.getMsg());
//                return exchange.getResponse().writeWith(Flux.just(this.getBodyBuffer(exchange.getResponse(), result)));
//            }
//        }
        return chain.filter(exchange);
    }











   /* private DataBuffer getBodyBuffer(ServerHttpResponse response, Result result) {
        return response.bufferFactory().wrap(JSONObject.toJSONBytes(result));
    }*/

    @Override
    public int getOrder() {
        return -200;
    }
}
