package com.dukoia.microservice.gateway.filters.auth;


import com.dukoia.microservice.gateway.common.ConstantField;
import com.dukoia.microservice.gateway.common.ResultCode;
import com.dukoia.microservice.gateway.config.globalfilterconfig.BaseGlobalResult;
import com.dukoia.microservice.gateway.pojo.ResultDto;
import com.dukoia.microservice.gateway.util.DateUtil;
import com.dukoia.microservice.gateway.util.UtilsParseUri;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

/**
 * @Author JefferyChang
 * @Date 2019/5/17 18 11
 * @Desp 进行用户安全校验
 */
@Component
@Slf4j
public class CheckAuthFilter extends BaseGlobalResult implements GlobalFilter, Ordered{


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        MultiValueMap<String, String> queryParams =
                exchange.getRequest().getQueryParams();
        ResultDto resultDto = UtilsParseUri.parseUri(queryParams);
        if(!resultDto.getFlag()){
           return  putMessageError(resultDto, ResultCode.ERROR_REQUEST_PARAM,exchange.getResponse());
        }else{
            List<String> list_traceId = exchange.getRequest().getHeaders().get(ConstantField.TRACE_ID);
            log.info("【>>>>>>>>>>requestId:{}被拦截,traceId:{}，时间{}】",
                    String.valueOf(resultDto.getDataMap().get(ConstantField.REQUEST_ID)),list_traceId.get(0),
                    DateUtil.format(DateUtil.DATA_FORMAT16,new Date()));
            resultDto = idempotentValidate(resultDto);
            if(!resultDto.getFlag()){
                return  putMessageError(resultDto, ResultCode.INFO_WORKING,exchange.getResponse());
            }else{
                resultDto = channelCheck(resultDto, exchange.getResponse());
                resultDto = signCheck(resultDto, exchange.getResponse());
                if(!resultDto.getFlag()){
                    return  putMessageError(resultDto, resultDto.getErrorCode(),resultDto.getErrorMsg()
                            ,exchange.getResponse());
                }else{
                    return chain.filter(exchange);
                }
            }
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
