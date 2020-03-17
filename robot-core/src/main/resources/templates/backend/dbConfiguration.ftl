package ${packagePath}.config;

import com.isyscore.ibo.neo.Neo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author ${user}
 * @since ${time}
 */
@Configuration
public class DbConfiguration {

    @Bean
    public Neo db(DataSource dataSource) {
        return Neo.connect(dataSource);
    }
}
