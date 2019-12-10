package com.dukoia.microservice.gateway.config.globalfilterconfig;

import com.alibaba.fastjson.JSON;
import com.dukoia.microservice.gateway.common.ConstantField;
import com.dukoia.microservice.gateway.common.RedisConstant;
import com.dukoia.microservice.gateway.common.ResultCode;
import com.dukoia.microservice.gateway.pojo.ConfigChannel;
import com.dukoia.microservice.gateway.pojo.MessageBean;
import com.dukoia.microservice.gateway.pojo.ResultDto;
import com.dukoia.microservice.gateway.service.ConfigChannelService;
import com.dukoia.microservice.gateway.util.RedisDao;
import com.dukoia.microservice.gateway.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author Dukoia
 * @createTime 2019/12/10 11:02
 */
@Slf4j
public class BaseGlobalResult extends  AbstractGlobalResult{
    @Resource
    private RedisDao redisDao;

    @Autowired
    private ConfigChannelService configChannelService;


    @Override
    protected ResultDto idempotentValidate(ResultDto resultDto) {
        String redisKey = ConstantField.REQUEST_ID + ":" + resultDto.getDataMap().get(ConstantField.REQUEST_ID);
        boolean exists = redisDao.exists(redisKey);
        Boolean flag = false;
        if(!exists){
            flag = true;
            redisDao.setKey(redisKey,ResultCode.INFO_WORKING.getDesc(),24, TimeUnit.HOURS);
        }
        resultDto.setFlag(flag);
        return resultDto;
    }

    @Override
    protected ResultDto channelCheck(ResultDto resultDto,ServerHttpResponse response) {
        //获取 channelOPenCode
        String channelOpenCode =
                String.valueOf(resultDto.getDataMap().get(ConstantField.CHANNEL_OPEN_CODE));
        //拼装key
        String redisKey = RedisConstant.COMBOCODE_CHANNELID + channelOpenCode;
        boolean exists = redisDao.exists(redisKey);
        ConfigChannel configChannel = null;
        if(!exists){
            //不存在 进行数据库查询
//            configChannel = configChannelService.findConfigChannelByChannelOpenCode(channelOpenCode);
            if(null == configChannel){
                putMessageError(resultDto,ResultCode.ERROR_UNDERWRITING_NONE,response);
            }else{
                //存入redis中
                redisDao.setKey(redisKey,JSON.toJSONString(configChannel));
            }
        }else{
            String value = redisDao.getValue(redisKey);
            configChannel = JSON.toJavaObject(JSON.parseObject(value), ConfigChannel.class);
        }
        resultDto.setFlag(true);
        resultDto.getDataList().add(configChannel);
        return resultDto;
    }

    @Override
    protected ResultDto signCheck(ResultDto resultDto, ServerHttpResponse response) {
        ConfigChannel configChannel =
                (ConfigChannel) resultDto.getDataList().get(0);
        String secret_key = configChannel.getSecretKey();
        //进行  访问超时校验
        Boolean overTimeFlag = SecurityUtil.checkOverTime(String.valueOf(resultDto.getDataMap().get(ConstantField.TIMESTAMP)),
                ConstantField.OVER_TIME);
        if(!overTimeFlag){
            log.error("【visit overTime :访问超时】");
            resultDto.setFlag(false);
            resultDto.setErrorMsg(ResultCode.ERROR_SERVICE_OVERTIME.getDesc());
            resultDto.setErrorCode(ResultCode.ERROR_SERVICE_OVERTIME.getCode());
            return resultDto;
        }
        // 进行 签名校验
        boolean signFlag = SecurityUtil.checkSignature(String.valueOf(resultDto.getDataMap().get(ConstantField.SIGNATURE)),
                secret_key, String.valueOf(resultDto.getDataMap().get(ConstantField.TIMESTAMP)),
                String.valueOf(resultDto.getDataMap().get(ConstantField.NONCE)));
        if(!signFlag){
            log.error("【sign check fail:签名认证失败】");
            resultDto.setFlag(false);
            resultDto.setErrorMsg(ResultCode.ERROR_REQUEST_SIGN.getDesc());
            resultDto.setErrorCode(ResultCode.ERROR_REQUEST_SIGN.getCode());
            return resultDto;
        }
        resultDto.setFlag(true);
        return resultDto;
    }


    @Override
    protected Mono<Void> putMessageError(ResultDto resultDto, ResultCode ResultCode,
                                         ServerHttpResponse response) {
        String requestId = String.valueOf(resultDto.getDataMap().get(ConstantField.REQUEST_ID));
        MessageBean messageBean = new MessageBean(requestId,Long.valueOf(ResultCode.getCode()),
                ResultCode.getDesc(),null);
        byte[] data = JSON.toJSONString(messageBean).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(data);
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    protected Mono<Void> putMessageError(ResultDto resultDto, String errorCode, String errorMsg, ServerHttpResponse response) {
        String requestId = String.valueOf(resultDto.getDataMap().get(ConstantField.REQUEST_ID));
        MessageBean messageBean = new MessageBean(requestId,Long.valueOf(errorCode.trim()),
                errorMsg,null);
        byte[] data = JSON.toJSONString(messageBean).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(data);
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
