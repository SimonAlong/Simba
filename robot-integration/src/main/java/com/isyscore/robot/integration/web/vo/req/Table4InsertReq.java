package com.isyscore.robot.integration.vo.req;

import lombok.Data;

/**
 * @author zhouzhenyong
 * @since 2020/03/17 19:39:28
 */
@Data
public class Table4InsertReq {


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
     * 排序
     */
    private Integer sort;
    /**
     * 枚举1
     */
    private String enum1;
    /**
     * 创建人名字
     */
    private String createUserName;
}
