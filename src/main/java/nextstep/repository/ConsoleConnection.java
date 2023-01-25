package nextstep.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConsoleConnection {
    private static final String URL = "jdbc:h2:~/test;AUTO_SERVER=true";
    private static final String USER = "sa";
    private static final String PW = "";

    public static Connection connect() {
        Connection con = null;
        try{
            con = DriverManager.getConnection(URL, USER, PW);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }
}
