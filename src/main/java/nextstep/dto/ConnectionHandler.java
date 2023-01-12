package nextstep.dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ComponentScan
public class ConnectionHandler {

    @Value("${spring.datasource.hikari.jdbc-url}")
    private String CONNECTION_URL;
    @Value("${spring.datasource.hikari.username}")
    private String USERNAME;

    @Value("${spring.datasource.hikari.password}")
    private String PASSWORD;
    private final Connection connection;

    public ConnectionHandler() {
        this.connection = connect();
    }

    private Connection connect() {
        Connection connection = null;
        // 드라이버 연결
        try {
            connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public void release() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }

    public PreparedStatement createPreparedStatement(String sql, String[] columnsNames) throws SQLException {
        return this.connection.prepareStatement(sql, columnsNames);
    }
}
