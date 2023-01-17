package nextstep.utils;

import nextstep.exception.JdbcException;

import java.sql.*;

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

    public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        close(pstmt, conn);

        try {
            if(rs != null && !rs.isClosed()) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Long getGeneratedKey(PreparedStatement pstmt) throws SQLException {
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new JdbcException("id 값이 존재하지 않습니다.");
        }

        return generatedKeys.getLong(1);
    }

}
