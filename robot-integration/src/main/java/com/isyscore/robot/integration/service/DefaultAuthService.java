package com.isyscore.robot.integration.service;

import com.isyscore.os.dev.api.permission.model.domain.AclDomain;
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

        // 添加一级菜单，其他请自行添加
        aclList.add(oneMenu());

        dataAuthAclDomain.setAcls(aclList);
    }

    /**
     * 返回一级菜单
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
        domain.setAclList(getResourceCodeListOneMenu());
        return domain;
    }

    /**
     * 第一级菜单的资源code
     */
    private List<AclDomain> getResourceCodeListOneMenu() {
        List<AclDomain> aclDomainList = new ArrayList<>();
        // 城市新增
        AclDomain domain1 = new AclDomain();
        domain1.setCode("add_city");
        domain1.setName("城市新增");
        domain1.setUrl("robot/city/add");
        // 1：菜单，2：按钮，3：其他，不过对于脚手架默认的前端这里type都一样
        domain1.setType(2);
        // 0：不可用，1：可用
        domain1.setStatus(1);
        domain1.setRemark("");
        aclDomainList.add(domain1);

        // 按钮编辑
        AclDomain domain2 = new AclDomain();
        domain2.setCode("can_edit");
        domain2.setName("编辑按钮可用");
        domain2.setUrl("robot/city/update");
        // 1：菜单，2：按钮，3：其他，不过对于脚手架默认的前端这里type都一样
        domain2.setType(2);
        // 0：不可用，1：可用
        domain2.setStatus(1);
        domain2.setRemark("");
        aclDomainList.add(domain2);

        // ... 还可以添加一个菜单中的更多处理
        return aclDomainList;
    }
}
