package nextstep;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConsoleConnectDB {

    private static Connection con = null;

    public static Connection getConnect(){
        if(con == null){
            try {
                con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true", "sa", "");
                System.out.println("정상적으로 연결되었습니다.");
                return con;
            } catch (SQLException e) {
                System.err.println("연결 오류:" + e.getMessage());
                e.printStackTrace();
            }
        }

        return con;
    }
}
