package com.dukoia.microservice.gateway.pojo;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dukoia
 * @createTime 2019/12/10 11:03
 */
@Data
public class MessageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String requestId;

    private String responseTime;

    private Long responseCode;

    private String responseMsg;

    private Map<String,Object> responseData = new HashMap<>();

    public MessageBean() {
    }

    public MessageBean(String requestId, String responseTime, Long responseCode, String responseMsg, Map<String, Object> responseData) {
        this.requestId = requestId;
        this.responseTime =responseTime;
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
        this.responseData = responseData;
    }

    public MessageBean(String requestId,Long responseCode, String responseMsg, Map<String, Object> responseData) {
        this.requestId = requestId;
        this.responseTime = DateUtil.format(new Date(),"yyyyMMddHHmmss");
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
        this.responseData = responseData;
    }
}
