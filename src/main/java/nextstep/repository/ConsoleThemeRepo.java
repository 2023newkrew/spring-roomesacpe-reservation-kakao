package nextstep.repository;

import nextstep.domain.theme.Theme;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsoleThemeRepo implements ThemeRepo {
    public long save(Theme theme) {
        Connection con = ConsoleConnection.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?)";
        Long id = null;

        try {
            ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return id;
    }

    public int delete(long id) {
        Connection con = ConsoleConnection.connect();
        PreparedStatement ps = null;

        String sql = "DELETE FROM theme WHERE id = ?";
        int result = 0;

        try {
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public Theme findById(long id) {
        Connection con = ConsoleConnection.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM theme WHERE id = ?";
        Theme theme = null;

        try {
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                theme = new Theme(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return theme;
    }

    public List<Theme> findAll() {
        Connection con = ConsoleConnection.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM theme";
        List<Theme> themes = new ArrayList<>();

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                themes.add(new Theme(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return themes;
    }
}
