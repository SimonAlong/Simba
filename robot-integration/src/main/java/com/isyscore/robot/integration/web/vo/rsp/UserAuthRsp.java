package com.isyscore.robot.integration.web.vo.rsp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author robot
 */
@Data
@Accessors(chain = true)
public class UserAuthRsp {

    /**
     * 权限code集合
     */
    private List<String> authCodeList;
}
