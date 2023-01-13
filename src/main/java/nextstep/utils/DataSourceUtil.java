package nextstep.utils;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceUtil {
    public static DataSource getDataSource() {
        Driver driver = null;
        try {
            driver = DriverManager.getDriver("jdbc:h2:tcp://localhost/~/test");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DataSource dataSource = new SimpleDriverDataSource(
                driver,
                "jdbc:h2:tcp://localhost/~/test",
                "sa",
                ""
        );

        return dataSource;
    }
}
