package com.isyscore.robot.integration.web.controller;

import com.alibaba.fastjson.JSON;
import com.isyscore.robot.integration.service.auth.AuthService;
import com.isyscore.robot.integration.web.vo.Response;
import com.isyscore.robot.integration.web.vo.rsp.UserAuthRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author robot
 */
@RequestMapping("robot/auth")
@RestController
public class AuthController extends BaseResponseController {

    @Autowired
    private AuthService authHandleService;

    /**
     * 给OS进行使用对应的菜单结构
     */
    @PostMapping("getAuthListForOs")
    public String getAuthList() {
        return JSON.toJSONString(authHandleService.getAuthConfigOfMenu());
    }

    /**
     * 获取用户当前的权限
     * <p>
    * 用于在用户请求某个页面的时候，将对应的权限返回给前端
    */
    @GetMapping("getAuthOfUser")
    public Response<UserAuthRsp> getAuthOfUser() {
        return success(authHandleService.getAuthOfUser());
    }
}
