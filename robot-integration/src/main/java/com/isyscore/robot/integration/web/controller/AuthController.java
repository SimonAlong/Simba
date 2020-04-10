package com.isyscore.robot.integration.web.controller;

import com.isyscore.os.dev.api.permission.model.builder.QueryUserAclRequestBuilder;
import com.isyscore.os.dev.api.permission.model.result.QueryUserAclResult;
import com.isyscore.os.dev.api.permission.service.PermissionService;
import com.isyscore.os.dev.util.IsyscoreHashMap;
import com.isyscore.os.sso.session.RequestUserHolder;
import com.isyscore.os.sso.session.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;

/**
 * @author shizi
 * @since 2020/4/10 3:44 PM
 */
@RequestMapping("robot/auth")
@RestController
public class AuthController {

    private static final String SID_STR = "X-Isyscore-Permission-Sid";

    @Autowired
    private PermissionService permissionService;

    // todo 只需要获取三个接口：1.获取用户详情，2.获取权限结构信息（给os使用），3.获取用户权限信息

    @GetMapping("getList")
    public void test1() {
        UserForm currentUser = RequestUserHolder.getCurrentUser();
        IsyscoreHashMap coreMap = new IsyscoreHashMap();
        coreMap.put(SID_STR, currentUser.getToken());

        QueryUserAclRequestBuilder builder = new QueryUserAclRequestBuilder()
        //设置应用 code: 指查询指定应用下的用户权限
        .setAppCode("appCode")
        //设置请求头
        .setHeaders(coreMap);

         QueryUserAclResult result = permissionService.queryUserAcl(builder);
        // 判断结果是否成功
        System.out.println("success: " + result.isSuccess());
        // 获取 httpCode:http 请求结果的 code 码
         System.out.println("httpCode: " + result.getResponse().getHttpCode());
        // 获取 httpMessage:http 请求结果的消息
         System.out.println("httpMessage: " + result.getResponse().getHttpMessage());
        // 获取 code:接口返回的错误码
         System.out.println("code: " + result.getResponse().getCode());
        // 获取 message:接口返回的错误消息
         System.out.println("message: " + result.getResponse().getMessage());
        // 获取 detail:接口返回的错误附带详情
         System.out.println("detail: " + result.getResponse().getDetail());
        // 获取结果数据
         System.out.println("data: " + result.getResponse().getBody());
        // 解析结果数据(Json 转 Object)
         System.out.println("parseData: " + result.parseData());
    }

    @GetMapping("getAuthListForOs")
    public String getAuthList(){
        // todo
        return "";
    }
}
