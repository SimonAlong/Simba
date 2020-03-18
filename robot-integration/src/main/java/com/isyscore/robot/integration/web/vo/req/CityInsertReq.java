package com.isyscore.robot.integration.web.vo.req;

import java.util.Date;
import lombok.Data;

/**
 * @author robot
 */
@Data
public class CityInsertReq {


    /**
     * '省份编码'
     */
    private String provinceCode;
    /**
     * '市编码'
     */
    private String cityCode;
    /**
     * '名称'
     */
    private String name;
    /**
     * '创建时间'
     */
    private Date createTime;
    /**
     * '更新时间'
     */
    private Date updateTime;
    /**
     * '中心点经纬度'
     */
    private String center;
    /**
     * 状态：1新地址，0老地址
     */
    private Integer status;
    /**
     * 创建人名字
     */
    private String createUserName;
}