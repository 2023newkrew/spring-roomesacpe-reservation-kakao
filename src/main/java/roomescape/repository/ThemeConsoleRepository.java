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
            String sql = "SELECT * FROM theme WHERE id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
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
        close(con);
        return theme;
    }

    @Override
    public int updateTheme(Theme theme) {
        int count = 0;
        Connection con = getConnection();
        try {
            String sql = "UPDATE theme SET name = ?, desc = ?, price = ? WHERE id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
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
            String sql = "DELETE FROM theme WHERE id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        close(con);
        return count;
    }

}
