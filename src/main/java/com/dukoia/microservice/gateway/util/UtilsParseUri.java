package com.dukoia.microservice.gateway.util;

import com.dukoia.microservice.gateway.pojo.ResultDto;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;

/**
 * @Author JefferyChang
 * @Date 2019/5/20 14 19
 * @Desp 解析uri 参数
 */
public class UtilsParseUri {

    private static final String[] CONSTANT_FIELD= {"requestId","timestamp","nonce","signature","channelOpenCode"};


    public static ResultDto parseUri(MultiValueMap<String, String> multiValueMap){
        ResultDto resultDto = new ResultDto();
        Boolean flag = true;
        List<String> list_args = Arrays.asList(CONSTANT_FIELD);
        for(String key : list_args){
            List<String> values = multiValueMap.get(key);
            if(null!=values && values.size()>0){
                resultDto.getDataMap().put(key,values.get(0));
            }else{
                flag=false;
                resultDto.getErrorMsgMap().put(key,
                        new StringBuffer(key).append(" is Null"));
                break;
            }
            resultDto.setFlag(flag);
        }
        if(resultDto.getDataMap().size() < CONSTANT_FIELD.length){
            flag =false;
            resultDto.setFlag(flag);
        }
        return resultDto;
    }







}
