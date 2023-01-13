package roomescape.repository;

import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseConsoleRepository {

    private static final String URL = "jdbc:h2:~/test;AUTO_SERVER=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }

    private void close(Connection con) {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }

    protected Long insert(final String SQL, final Object... args) {
        Connection con = getConnection();
        Long id = null;
        try {
            PreparedStatement ps = con.prepareStatement(SQL, new String[]{"id"});
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            ResultSet rs = ps.getGeneratedKeys();
            id = rs.getLong("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        close(con);
        return id;
    }

    protected <T> List<T> query(final String SQL, final RowMapper<T> rowMapper, final Object... args) {
        Connection con = getConnection();
        ResultSet rs = null;
        List<T> results = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(SQL);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            int count = 1;
            while(rs.next()) {
                T result = rowMapper.mapRow(rs, count++);
                results.add(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        close(con);
        return results;
    }

    protected int update(final String SQL, final Object... args) {
        int count = 0;
        Connection con = getConnection();
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(SQL);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        close(con);
        return count;
    }

    protected int delete(final String SQL, final Object... args) {
        return update(SQL, args);
    }

}
