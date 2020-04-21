package com.isyscore.robot.integration.service.auth;

import com.isyscore.os.dev.api.permission.model.domain.AclDomain;
import com.isyscore.os.dev.api.permission.model.domain.MenuAuthorityDomain;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shizi
 * @since 2020/4/21 10:02 AM
 */
@Service
public class CityMenuAuthHandler implements MenuAuthHandler {

    @Override
    public MenuAuthorityDomain getMenuAuth() {
        MenuAuthorityDomain domain = new MenuAuthorityDomain();
        domain.setCode("cityList");
        domain.setName("城市配置");
        // 编号排序
        domain.setSeq(1);
        // 0：不可用，1：可用
        domain.setStatus(1);
        domain.setRemark("");

        // 添加当前菜单中对应的资源
        domain.setAclList(getResourceCodeList());
        return domain;
    }

    /**
     * 当前菜单资源的code权限
     */
    private List<AclDomain> getResourceCodeList() {
        List<AclDomain> aclDomainList = new ArrayList<>();
        // 城市新增
        AclDomain domain1 = new AclDomain();
        domain1.setCode("city_add");
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
        domain2.setCode("city_edit");
        domain2.setName("编辑按钮可用");
        domain2.setUrl("robot/city/update");
        // 1：菜单，2：按钮，3：其他，不过对于脚手架默认的前端这里type都一样
        domain2.setType(2);
        // 0：不可用，1：可用
        domain2.setStatus(1);
        domain2.setRemark("");
        aclDomainList.add(domain2);

        return aclDomainList;
    }
}
