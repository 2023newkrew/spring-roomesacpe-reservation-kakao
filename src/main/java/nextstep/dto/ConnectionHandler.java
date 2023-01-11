package nextstep.dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionHandler {

    private static final String CONNECTION_URL = "jdbc:h2:tcp://localhost:1521/test;AUTO_SERVER=true";

    private static final String USERNAME = "sa";

    private static final String PASSWORD = "";

    private Connection connection;

    public ConnectionHandler() throws SQLException {
        connect();
    }

    private void connect() throws SQLException {
        connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
    }

    public void release() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    public PreparedStatement createPreparedStatement(String sql, String[] columnsNames) throws SQLException {
        return this.connection.prepareStatement(sql, columnsNames);
    }
}
