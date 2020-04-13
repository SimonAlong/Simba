package com.isyscore.robot.integration;

import com.isyscore.os.dev.api.http.DefaultIsyscoreClient;
import com.isyscore.os.dev.api.http.IsyscoreClient;
import com.isyscore.os.dev.api.permission.model.builder.QueryUserAclRequestBuilder;
import com.isyscore.os.dev.api.permission.model.result.QueryUserAclResult;
import com.isyscore.os.dev.api.permission.service.PermissionService;
import com.isyscore.os.dev.api.permission.service.impl.PermissionServiceImpl;
import com.isyscore.os.dev.config.IsyscoreConstants;
import com.isyscore.os.dev.util.IsyscoreHashMap;
import com.isyscore.os.sso.session.RequestUserHolder;
import com.isyscore.os.sso.session.UserForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;

/**
 * @author shizi
 * @since 2020/4/10 11:04 AM
 */
@SpringBootConfiguration
public class AuthTest  {
}
