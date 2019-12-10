package com.dukoia.microservice.gateway.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author:JefferyChang
 * @Date:2019/5/21 14:20
 * @Desp:
 */
@Data
@Entity
@Table(name = "config_channel")
public class ConfigChannel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "version")
    private Integer version;
    @Column(name = "updated_time")
    private Date updatedTime;
    @Column(name = "created_time")
    private Date createdTime;
    @Column(name = "handler_server")
    private String handlerServer;


    @Column(name = "client_ip")
    private String clientIp;
    @Column(name = "subsequent_url")
    private String subsequentUrl;
    @Column(name = "issuing_url")
    private String issuingUrl;
    @Column(name = "policy_url")
    private String policyUrl;
    @Column(name = "manager_oa")
    private String managerOa;


    @Column(name = "certification_no")
    private String certificationNo;
    @Column(name = "certification_type")
    private String certificationType;
    @Column(name = "secret_key")
    private String secretKey;
    @Column(name = "channel_open_code")
    private String channelOpenCode;
    @Column(name = "channel_name")
    private String channelName;

    @Column(name = "channel_code")
    private String channelCode;


}
