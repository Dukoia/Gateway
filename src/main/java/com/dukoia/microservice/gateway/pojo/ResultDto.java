package com.dukoia.microservice.gateway.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dukoia
 * @createTime 2019/12/10 11:00
 */
@Data
public class ResultDto implements Serializable {

    private Boolean flag;

    private List<Object> dataList = new ArrayList<>(10);

    private Map<String,Object> dataMap = new HashMap<>(16);

    private Map<String,Object> errorMsgMap =new HashMap<>(16);

    private String errorCode;

    private String errorMsg;

    public ResultDto() {
    }

    public ResultDto(Boolean flag, String errorMsg) {
        this.flag = flag;
        this.errorMsg = errorMsg;
    }

    public ResultDto(Boolean flag, List<Object> dataList, Map<String, Object> dataMap, Map<String, Object> errorMsgMap) {
        this.flag = flag;
        this.dataList = dataList;
        this.dataMap = dataMap;
        this.errorMsgMap = errorMsgMap;
    }
}
