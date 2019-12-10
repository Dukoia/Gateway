package com.dukoia.microservice.gateway.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author JefferyChang
 * @Date 2019/5/17 19 30
 * @Desp
 */
@Data
public class Response implements Serializable {

    private String code;

    private String message;

    private String data;




}
