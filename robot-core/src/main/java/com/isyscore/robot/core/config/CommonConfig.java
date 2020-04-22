package com.isyscore.robot.core.config;

import com.isyscore.isc.neo.Neo;
import com.isyscore.isc.neo.NeoMap;
import com.isyscore.isc.neo.StringConverter;
import lombok.Data;

/**
 * @author shizi
 * @since 2020/4/22 2:33 PM
 */
@Data
public class CommonConfig implements ConfigContext {

    /**
     * 应用名字，用于前端创建目录用，建议用一个小写的单词
     */
    private String appName;
    /**
     * 应用名英文
     */
    private String appNameCn;
    /**
     * 不需要生成的表
     */
    private String dbUrl;
    private String dbUserName;
    private String dbUserPassword;

    private Neo neo;

    /**
     * 前端配置的后端的端口
     */
    private String backendPort;

    @Override
    public void visit(NeoMap dataMap) {
        if (null == dataMap) {
            return;
        }
        dataMap.put("appName", appName);
        dataMap.put("AppName", StringConverter.underLineToBigCamel(appName));
        dataMap.put("appNameCn", appNameCn);
        dataMap.put("dbUrl", dbUrl);
        dataMap.put("dbUserName", dbUserName);
        dataMap.put("dbUserPassword", dbUserPassword);
        dataMap.put("backendPort", backendPort);

        if (null == neo) {
            neo = Neo.connect(dbUrl, dbUserName, dbUserPassword);
        }
        dataMap.put("db", neo);
    }
}
