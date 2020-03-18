package com.isyscore.robot.integration.web.vo;

import com.isyscore.ibo.mikilin.annotation.Check;
import lombok.Data;

/**
 * @author robot
 */
@Data
public class Pager<T> {

    private Integer pageNo;
    private Integer pageSize;
    @Check
    private T param;

    public Integer getPageIndex() {
        return pageNo > 1 ? (pageNo - 1) * pageSize : 0;
    }
}
