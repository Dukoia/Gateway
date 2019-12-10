package com.dukoia.microservice.gateway.config.globalfilterconfig;

import com.dukoia.microservice.gateway.common.ResultCode;
import com.dukoia.microservice.gateway.pojo.ResultDto;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 * @author Dukoia
 * @createTime 2019/12/10 10:59
 */
public abstract class AbstractGlobalResult {

    /**
     * 幂等校验
     * @param resultDto
     * @return
     */
    protected abstract ResultDto idempotentValidate(ResultDto resultDto);
    /**
     * 渠道校验
     * @param resultDto
     * @param response
     * @return
     */
    protected abstract ResultDto channelCheck(ResultDto resultDto, ServerHttpResponse response);
    /**
     * 签名校验
     * @param resultDto
     * @param response
     * @return
     */
    protected abstract ResultDto signCheck(ResultDto resultDto,ServerHttpResponse response);

    //protected abstract void putMessageSuccess();

    /**
     * 错误信息返回
     * @param resultDto
     * @param enumResultCode
     * @param response
     * @return
     */
    protected abstract Mono<Void> putMessageError(ResultDto resultDto, ResultCode enumResultCode
            , ServerHttpResponse response);

    /**
     * 根据异常code码和异常信息进行数据封装返回
     * @param resultDto
     * @param errorCode
     * @param errorMsg
     * @param response
     * @return
     */
    protected abstract Mono<Void> putMessageError(ResultDto resultDto,String  errorCode,
                                                  String errorMsg ,ServerHttpResponse response);
}
