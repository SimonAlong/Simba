package com.isyscore.robot.integration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author shizi
 * @since 2020/4/10 11:12 AM
 */
@Getter
@Setter
@ConfigurationProperties("isyscore")
public class PermissionProperties {

    /**
     * API 网关地址
     */
    private String serverHost;
    /**
     * APPID
     */
    private String appId;
    /**
     * 数据交互格式
     */
    private String format;
    /**
     * 字符集
     */
    private String charset;
    /**
     * signType
     */
    private String signType;
    /**
     * 加密类型
     */
    private String encryptType;
    /**
     * 本地操作系统
     */
    private Integer osId;
}
