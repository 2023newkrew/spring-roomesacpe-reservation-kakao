package nextstep.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static final String DB_URL = "jdbc:h2:~/test;AUTO_SERVER=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private DatabaseUtil(){}

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            return null;
        }
    }

    public static void close(AutoCloseable... closeables){
        for (AutoCloseable closeable : closeables) {
            try {
                closeable.close();
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
    }
}

