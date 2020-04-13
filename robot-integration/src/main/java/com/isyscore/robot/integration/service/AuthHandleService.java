package com.isyscore.robot.integration.service;

import com.alibaba.fastjson.JSON;
import com.isyscore.common.exception.BusinessException;
import com.isyscore.os.dev.api.domain.AclModuleLevelDomain;
import com.isyscore.os.dev.api.domain.DataAuthAclDomain;
import com.isyscore.os.dev.api.permission.model.builder.QueryUserAclRequestBuilder;
import com.isyscore.os.dev.api.permission.model.result.QueryUserAclResult;
import com.isyscore.os.dev.api.permission.service.PermissionService;
import com.isyscore.os.dev.util.IsyscoreHashMap;
import com.isyscore.os.sso.session.RequestUserHolder;
import com.isyscore.os.sso.session.UserForm;
import com.isyscore.robot.integration.web.vo.rsp.UserAuthRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户权限给前端的处理
 *
 * @author shizi
 * @since 2020/4/13 4:12 PM
 */
@Slf4j
@Service
public class AuthHandleService {

    private static final String SID_STR = "X-Isyscore-Permission-Sid";
    @Autowired
    private PermissionService permissionService;
    @Value("${debug.sid}")
    private Integer debugSID;

    public UserAuthRsp getAuthOfUser() {
        UserForm currentUser = RequestUserHolder.getCurrentUser();
        IsyscoreHashMap coreMap = new IsyscoreHashMap();
        coreMap.put(SID_STR, currentUser.getToken());
        if (null != debugSID) {
            coreMap.put(SID_STR, debugSID);
        }

        QueryUserAclRequestBuilder builder = new QueryUserAclRequestBuilder().setAppCode("robot").setHeaders(coreMap);
        QueryUserAclResult result = permissionService.queryUserAcl(builder);
        if (result.isSuccess()) {
            DataAuthAclDomain dataAuthAclDomain = JSON.parseObject(result.getResponse().getBody(), DataAuthAclDomain.class);
            if (null != dataAuthAclDomain) {
                UserAuthRsp rsp = new UserAuthRsp();
                rsp.setMenuAuthList(getCodeList(dataAuthAclDomain, 0));
                rsp.setResourceAuthList(getCodeList(dataAuthAclDomain, 1));
                return rsp;
            } else {
                log.error("数据解析异常：" + JSON.toJSONString(result.getResponse()));
                throw new BusinessException("数据解析异常");
            }
        } else {
            log.error("获取用户权限异常：" + JSON.toJSONString(result.getResponse()));
            throw new BusinessException("获取数据异常");
        }
    }

    /**
     * 获取对应的code集合
     *
     * @param menuOrResource 0：menu, 1：菜单
     */
    private List<String> getCodeList(DataAuthAclDomain dataAuthAclDomain, Integer menuOrResource) {
        List<String> menuCodeList = new ArrayList<>();
        if (null == dataAuthAclDomain) {
            return menuCodeList;
        }

        menuCodeList.addAll(doGetCodeList(dataAuthAclDomain.getAclsList(), menuOrResource));
        return menuCodeList;
    }

    private List<String> doGetCodeList(List<AclModuleLevelDomain> aclList, Integer menuOrResource) {
        if (CollectionUtils.isEmpty(aclList)) {
            return Collections.emptyList();
        }

        return aclList.stream().filter(e -> e.getStatus() == 1).flatMap(e -> {
            List<String> dataList = new ArrayList<>();
            if(0 == menuOrResource){
                // 1：表示当前code为菜单
                if (e.getType() == 1) {
                    dataList.add(e.getCode());
                }
            }else{
                // 非1：表示当前code为非菜单类型的资源
                if (e.getType() != 1) {
                    dataList.add(e.getCode());
                }
            }

            dataList.addAll(doGetCodeList(e.getAclModuleList(), menuOrResource));
            return dataList.stream();
        }).collect(Collectors.toList());
    }
}
