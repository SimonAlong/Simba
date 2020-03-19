package com.isyscore.robot.integration.web.vo.req;

import lombok.Data;

/**
 * @author robot
 */
@Data
public class BusinessCityQueryReq {


    /**
     * 省份编码
     */
    private String provinceCode;
    /**
     * 市编码
     */
    private String cityCode;
    /**
     * 名称
     */
    private String name;
}
