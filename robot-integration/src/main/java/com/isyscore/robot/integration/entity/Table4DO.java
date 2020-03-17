package com.isyscore.robot.integration.entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import com.isyscore.ibo.neo.annotation.Column;
import lombok.experimental.Accessors;
import lombok.Data;

/**
 * @author robot
 */
@Data
@Accessors(chain = true)
public class Table4DO {

    @Column("id")
    private Long id;

    /**
     * 数据来源组，外键关联lk_config_group
     */
    @Column("group")
    private String group;

    /**
     * 任务name
     */
    @Column("name")
    private String name;

    /**
     * 修改人名字
     */
    @Column("user_name")
    private String userName;
    @Column("age")
    private Integer age;
    @Column("n_id")
    private Long nId;
    @Column("sort")
    private Integer sort;

    /**
     * 类型：Y=成功；N=失败
     */
    @Column("enum1")
    private String enum1;
    @Column("create_time")
    private Timestamp createTime;
    @Column("time")
    private Time time;
    @Column("year")
    private Date year;
    @Column("date")
    private Date date;
    @Column("datetime")
    private Timestamp datetime;
}
