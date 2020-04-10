package com.isyscore.robot.integration.config;

import com.isyscore.os.dev.api.http.DefaultIsyscoreClient;
import com.isyscore.os.dev.api.http.IsyscoreClient;
import com.isyscore.os.dev.api.permission.service.PermissionService;
import com.isyscore.os.dev.api.permission.service.impl.PermissionServiceImpl;
import com.isyscore.robot.integration.properties.PermissionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shizi
 * @since 2020/4/10 11:23 AM
 */
@EnableConfigurationProperties(PermissionProperties.class)
@Configuration
public class OsConfiguration {

    @Bean
    public PermissionService testAEntity(PermissionProperties permissionProperties) {
        String serverHost = permissionProperties.getServerHost();
        String appId = permissionProperties.getAppId();
        String format = permissionProperties.getFormat();
        String charset = permissionProperties.getCharset();
        String signType = permissionProperties.getSignType();
        String encryptType = permissionProperties.getEncryptType();

        IsyscoreClient client = new DefaultIsyscoreClient(serverHost, appId, format, charset, signType, encryptType);
        return new PermissionServiceImpl(client);
    }
}
