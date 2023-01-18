package nextstep.console.util;

import java.sql.*;

public class DBConnectionUtil {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true", "sa", "");
    }

    public static void closeOperation(Connection conn, PreparedStatement pstmt, ResultSet resultSet){
        try{
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
        } catch (SQLException e) {}
        try{
            if (pstmt != null) {
                pstmt.close();
                pstmt = null;
            }
        } catch (SQLException e){}
        try {
            if (conn != null){
                conn.close();
                conn = null;
            }
        } catch (SQLException e){}
    }
}
