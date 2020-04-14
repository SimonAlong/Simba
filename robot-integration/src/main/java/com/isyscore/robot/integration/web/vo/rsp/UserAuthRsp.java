package com.isyscore.robot.integration.web.vo.rsp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author shizi
 * @since 2020/4/13 4:06 PM
 */
@Data
@Accessors(chain = true)
public class UserAuthRsp {

    /**
     * 权限code集合
     */
    private List<String> authCodeList;
}
