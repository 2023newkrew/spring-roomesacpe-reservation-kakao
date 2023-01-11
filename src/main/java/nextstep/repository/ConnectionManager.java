package nextstep.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String DB_CONNECTION_URL = "jdbc:h2:mem:test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(DB_CONNECTION_URL, DB_USER, DB_PASSWORD);
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }

    public static void closeAll(AutoCloseable... closeables) {
        for (AutoCloseable closeable : closeables) {
            close(closeable);
        }
    }

    private static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                System.err.println("오류:" + e.getMessage());
            }
        }
    }
}
