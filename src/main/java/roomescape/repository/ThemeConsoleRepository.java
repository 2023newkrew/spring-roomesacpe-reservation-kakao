package roomescape.repository;

import roomescape.domain.Theme;

import java.sql.*;

public class ThemeConsoleRepository extends BaseConsoleRepository implements ThemeRepository {

    @Override
    public Long createTheme(Theme theme) {
        Connection con = getConnection();
        Long id = null;
        try {
            String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            id = rs.getLong("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        close(con);
        return id;
    }

    @Override
    public Theme findThemeById(Long id) {
        Theme theme = null;
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(FIND_THEME_BY_ID_SQL);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                theme = new Theme(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("desc"),
                        rs.getInt("price")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public int updateTheme(Theme theme) {
        int count = 0;
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(UPDATE_THEME_SQL);
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            ps.setLong(4, theme.getId());
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        close(con);
        return count;
    }

    @Override
    public int deleteTheme(Long id) {
        int count = 0;
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(DELETE_THEME_SQL);
            ps.setLong(1, id);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        close(con);
        return count;
    }

}
