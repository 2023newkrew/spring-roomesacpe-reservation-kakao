package roomescape.reservation.repository.console;

import roomescape.reservation.domain.Theme;
import roomescape.reservation.repository.common.AbstractThemeH2Repository;
import roomescape.reservation.repository.common.ThemeMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThemeRepositoryConsole extends AbstractThemeH2Repository {

    private Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "");
            //System.out.println("정상적으로 연결되었습니다.");
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

    @Override
    public Theme add(Theme theme) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(insertQuery, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            theme.setId(rs.getLong("id"));
            return theme;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(con);
        }
    }

    @Override
    public List<Theme> get() {
        Connection con = getConnection();
        List<Theme> themes = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(selectQuery);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                themes.add(new ThemeMapper().mapRow(rs, rs.getRow()));
            }
            if (themes.isEmpty()) return null;
            return themes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con);
        }
    }

    @Override
    public Theme get(Long id) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(selectByIdQuery);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ThemeMapper().mapRow(rs, rs.getRow());
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con);
        }
    }

    @Override
    public Theme get(String name) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(selectByNameQuery);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ThemeMapper().mapRow(rs, rs.getRow());
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con);
        }
    }

    @Override
    public void remove(Long id) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(deleteByIdQuery);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con);
        }
    }
}
