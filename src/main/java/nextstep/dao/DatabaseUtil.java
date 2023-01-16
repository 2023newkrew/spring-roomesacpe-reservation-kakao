package nextstep.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;

public class DatabaseUtil {

    @Value("${spring.datasource.url}")
    private static String DATABASE_URL;

    @Value("${spring.datasource.username}")
    private static String USERNAME ;

    @Value("${spring.datasource.password}")
    private static String PASSWORD;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }

    public static void close(AutoCloseable... closeables) {
        for (AutoCloseable closeable : closeables) {
            try {
                if (closeable != null) {
                    closeable.close();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}

