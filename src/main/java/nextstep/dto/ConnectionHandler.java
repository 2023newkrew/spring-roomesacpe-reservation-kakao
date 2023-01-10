package nextstep.dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionHandler {

    private static final String CONNECTION_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
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
