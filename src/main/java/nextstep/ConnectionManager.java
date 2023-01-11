package nextstep;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class ConnectionManager {
    private Connection con = null;

    public ConnectionManager() {
        create();
    }

    public Connection get() {
        try {
            if (Objects.isNull(con) || con.isClosed()) {
                create();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public void close() {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            System.err.println("Error when closing connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void create() {
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=TRUE", "sa", "");
            assert con != null;
        } catch (SQLException | AssertionError e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
