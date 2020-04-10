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
        return Neo.connect(dataSource);
    }
}
