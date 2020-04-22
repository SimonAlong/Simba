package com.isyscore.robot.integration.web.vo.rsp;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;

/**
 * @author robot
 */
@Data
public class CityQueryRsp implements Serializable {


    /**
     * 主键id
     */
    private Integer id;
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
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 中心点经纬度
     */
    private String center;
}
