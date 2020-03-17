package com.isyscore.robot.integration.web.vo.rsp;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;

/**
 * @author zhouzhenyong
 * @since 2020/03/17 20:33:25
 */
@Data
public class Table4QueryRsp implements Serializable {


    /**
     * 命名空间
     */
    private Long id;
    /**
     * 分组
     */
    private String group;
    /**
     * 名字
     */
    private String name;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * n_id
     */
    private Long nId;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 枚举1
     */
    private String enum1;
    /**
     * create_time
     */
    private Date createTime;
    /**
     * time时间
     */
    private Date time;
    /**
     * 年
     */
    private Date year;
    /**
     * data时间
     */
    private Date date;
    /**
     * datetime时间
     */
    private Date datetime;
}
