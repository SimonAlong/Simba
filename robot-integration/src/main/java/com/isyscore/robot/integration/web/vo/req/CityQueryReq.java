package com.isyscore.robot.integration.web.vo.req;

import com.isyscore.ibo.mikilin.annotation.Matcher;
import lombok.Data;

/**
 * @author robot
 */
@Data
public class CityQueryReq {


    /**
     * 省份编码
     */
    private String provinceCode;
    /**
     * 市编码
     */
    @Matcher(value = "12")
    private String cityCode;
    /**
     * 名称
     */
    private String name;
}
