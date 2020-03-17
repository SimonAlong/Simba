package com.isyscore.robot.integration.config;

import com.isyscore.ibo.neo.Neo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author shizi
 * @since 2020/3/17 下午8:37
 */
@Configuration
public class DbConfiguration {

    @Bean
    public Neo db(DataSource dataSource) {
        return Neo.connect(dataSource);
    }
}
