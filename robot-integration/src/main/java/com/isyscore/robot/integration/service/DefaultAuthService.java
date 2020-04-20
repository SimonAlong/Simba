package com.isyscore.robot.integration.service;

import com.isyscore.os.dev.api.permission.model.domain.MenuAuthorityDomain;
import com.isyscore.os.dev.api.permission.model.domain.MenuDomain;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自行添加对应的界面上的权限
 *
 * @author shizi
 * @since 2020/4/14 3:37 PM
 */
@Service
public class DefaultAuthService extends AbstractAuthService {

    @Override
    protected void doAddAuth(MenuDomain dataAuthAclDomain) {
        List<MenuAuthorityDomain> aclList = new ArrayList<>();

        aclList.add(oneMenu());

        dataAuthAclDomain.setAcls(aclList);
    }

    /**
     * 返回菜单
     */
    private MenuAuthorityDomain oneMenu() {
        MenuAuthorityDomain domain = new MenuAuthorityDomain();
        domain.setCode("cityList");
        domain.setName("城市配置");
        // 编号排序
        domain.setSeq(1);
        // 0：不可用，1：可用
        domain.setStatus(1);
        domain.setRemark("");
        return domain;
    }
}
