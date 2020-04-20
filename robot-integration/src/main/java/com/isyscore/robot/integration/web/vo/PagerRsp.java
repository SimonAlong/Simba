package com.isyscore.robot.integration.web.vo;

import lombok.Data;

/**
 * @author robot
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
