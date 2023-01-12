package nextstep.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface JdbcApiDAO {
    String DATASOURCE_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";
    String DATASOURCE_USER = "sa";
    String DATASOURCE_PASSWORD = "";

    default Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(DATASOURCE_URL, DATASOURCE_USER, DATASOURCE_PASSWORD);
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }
}
