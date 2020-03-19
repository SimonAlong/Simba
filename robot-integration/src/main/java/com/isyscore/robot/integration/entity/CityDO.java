package com.isyscore.robot.integration.entity;

import java.sql.Timestamp;
import com.isyscore.ibo.neo.annotation.Column;
import com.isyscore.ibo.neo.annotation.Table;
import lombok.experimental.Accessors;
import lombok.Data;

/**
 * 城市表
 * @author robot
 */
@Data
@Table("neo_city")
@Accessors(chain = true)
public class CityDO {


    /**
     * 主键id
     */
    @Column("id")
    private Integer id;

    /**
     * 省份编码
     */
    @Column("province_code")
    private String provinceCode;

    /**
     * 市编码
     */
    @Column("city_code")
    private String cityCode;

    /**
     * 名称
     */
    @Column("name")
    private String name;

    /**
     * 创建时间
     */
    @Column("create_time")
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @Column("update_time")
    private Timestamp updateTime;

    /**
     * 中心点经纬度
     */
    @Column("center")
    private String center;

    /**
     * 状态：1新地址，0老地址
     */
    @Column("status")
    private Integer status;
}
