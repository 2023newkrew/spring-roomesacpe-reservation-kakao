package roomescape.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

public class AppConfig {
    public DataSource getDataSource() throws ClassNotFoundException {
        YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
        yamlFactory.setResources(new ClassPathResource("application.yaml"));
        Properties props = yamlFactory.getObject();
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        String url = Objects.requireNonNull(props).getProperty("spring.datasource.url");
        String username = props.getProperty("spring.datasource.username");
        String password = props.getProperty("spring.datasource.password");
        String driverClassName = props.getProperty("spring.datasource.driver-class-name");
        Class.forName(driverClassName);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        driverManagerDataSource.setDriverClassName(driverClassName);
        return driverManagerDataSource;
    }
}
