package nextstep.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcUtils {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void close(PreparedStatement pstmt, Connection conn) {
        try {
            if(pstmt != null && !pstmt.isClosed()) {
                pstmt.close();
            }
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
