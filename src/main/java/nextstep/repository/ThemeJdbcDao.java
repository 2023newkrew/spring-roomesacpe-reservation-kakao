package nextstep.repository;

import nextstep.domain.Theme;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ThemeJdbcDao implements ThemeDao{
    private Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }

    private static void closeConnection(Connection con) {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }

    @Override
    public Long save(Theme theme) {
        Connection con = getConnection();

        long id;
        try {
            PreparedStatement ps = getPreparedStatementCreatorForSave(theme).createPreparedStatement(con);
            ps.executeQuery();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            id = rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
        return id;
    }

    @Override
    public Optional<Theme> findByName(String name) {
        Optional<Theme> theme;
        Connection con = getConnection();

        try {
            String sql = "SELECT * FROM theme WHERE name = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            theme = getThemesFromResultSet(rs).stream().findAny();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
        return theme;
    }

    private List<Theme> getThemesFromResultSet(ResultSet rs) throws SQLException {
        int NO_MEANING = 0;
        List<Theme> themes = new ArrayList<>();
        while (rs.next()) {
            themes.add(getRowMapper().mapRow(rs, NO_MEANING));
        }
        return themes;
    }

    @Override
    public Optional<Theme> findById(Long id) {
        Optional<Theme> theme;
        Connection con = getConnection();

        try {
            String sql = "SELECT * FROM theme WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            theme = getThemesFromResultSet(rs).stream().findAny();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
        return theme;
    }

    @Override
    public List<Theme> findAll() {
        List<Theme> themes;
        Connection con = getConnection();

        try {
            String sql = "SELECT * FROM theme";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            themes = getThemesFromResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
        return themes;
    }

    @Override
    public void update(Theme theme) {
        Connection con = getConnection();

        try {
            PreparedStatement ps = getPreparedStatementCreatorForUpdate(theme).createPreparedStatement(con);
            ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
    }

    @Override
    public void delete(Long id) {
        Connection con = getConnection();

        try {
            String sql = "DELETE FROM theme WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
    }
}
