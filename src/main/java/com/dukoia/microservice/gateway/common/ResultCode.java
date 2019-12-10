package com.dukoia.microservice.gateway.common;

/**
 * @author Dukoia
 * @createTime 2019/12/10 10:47
 */
public enum ResultCode {

    ERROR_REQUEST_SIGN("020001", "安全验证错误，报文签名验证失败"),
    ERROR_REQUEST_PARAM("020004", "参数错误，关键信息缺失"),
    ERROR_SERVICE_OVERTIME("010404", "请求超时"),
    ERROR_UNDERWRITING_NONE("030015", "没有该渠道产品"),
    INFO_WORKING("000201", "请求正在进行中,请稍后再试"),
    SUCCESS("000200", "成功"),
    FINISH("000207", "请求已完成"),
    ERROR_CORE_MQERROR("100999", "mq信息未正确发送");

    private String code;
    private String desc;

    ResultCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
