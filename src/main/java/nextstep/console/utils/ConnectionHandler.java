package nextstep.console.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionHandler {

    private static String CONNECTION_URL;

    private static String USERNAME;

    private static String PASSWORD;

    private final Connection connection;

    static {
        FileReader reader;
        try {
            reader = new FileReader("src/main/resources/application.yml");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("jdbc-url")) {
                    CONNECTION_URL = parseTokens(line.strip().split(" "));
                }
                if (line.contains("username")) {
                    USERNAME = parseTokens(line.strip().split(" "));
                }
                if (line.contains("password")) {
                    PASSWORD = parseTokens(line.strip().split(" "));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static String parseTokens(String[] tokens) {
        if (tokens.length == 1) {
            return "";
        }
        return tokens[1];
    }

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
