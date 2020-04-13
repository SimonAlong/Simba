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
     * 菜单权限对应的code集合
     */
    private List<String> menuAuthList;
    /**
     * 资源（按钮或者链接等等操作资源）权限菜单对应的code集合
     */
    private List<String> resourceAuthList;
}
