package ${packagePath}.web.controller;

import ${packagePath}.web.vo.AccountInfo;
import ${packagePath}.web.vo.req.LoginReq;
import ${packagePath}.web.vo.rsp.LoginRsp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author robot
 */
@RequestMapping("${appName}/login")
@RestController
public class LoginController {

    @PostMapping("account")
    public LoginRsp account(@RequestBody LoginReq loginReq) {
        // todo 这里请修改为自己想要的登录和权限，（如果不满足，则可以重写登录）
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setDisplayName(loginReq.getUserName());
        accountInfo.setCn(loginReq.getUserName());
        LoginRsp rsp = LoginRsp.build(accountInfo);

        List<String> authList = new ArrayList<>();
        // todo 这里后续请修改具体的权限
        authList.add("admin");
        rsp.setCurrentAuthority(authList);
        return rsp;
    }
}
