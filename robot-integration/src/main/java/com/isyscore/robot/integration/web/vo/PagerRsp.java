package com.isyscore.robot.integration.web.vo;

import lombok.Data;

import java.util.List;

/**
 * @author shizi
 * @since 2020/4/20 2:55 PM
 */
@Data
public class PagerRsp<T> {

    /**
     * 分页数据
     */
    private T dataList;
    /**
     * 总个数
     */
    private Integer totalNum;
}
