package com.isyscore.robot.integration.web.vo;

import lombok.Data;

/**
 * @author zhouzhenyong
 * @since 2020/03/17 20:58:15
 */
@Data
public class Pager<T> {

    private Integer pageNo;
    private Integer pageSize;
    private T param;

    public Integer getPageIndex() {
        return pageNo > 1 ? (pageNo - 1) * pageSize : 0;
    }
}
