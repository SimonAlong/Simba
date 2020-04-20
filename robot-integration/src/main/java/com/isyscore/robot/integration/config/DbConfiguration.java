package com.isyscore.robot.integration.config;

import com.isyscore.isc.neo.Neo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author robot
 */
@Configuration
public class DbConfiguration {

    @Bean
    public Neo db(DataSource dataSource) {
        Neo neo = Neo.connect(dataSource);
        // 关闭 explain 解析
        neo.setExplainFlag(false);
        // 关闭 sql 耗时监控
        // neo.setMonitorFlag(false);
        // 关闭 sql 规范校验
        neo.setStandardFlag(false);
        return neo;
    }
}
