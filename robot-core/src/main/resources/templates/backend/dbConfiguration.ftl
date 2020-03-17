package ${packagePath}.config;

import com.isyscore.ibo.neo.Neo;
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
