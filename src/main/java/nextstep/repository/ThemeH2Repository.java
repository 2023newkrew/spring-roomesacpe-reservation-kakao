package nextstep.repository;

import nextstep.domain.Theme;
import nextstep.exception.ThemeNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nextstep.repository.ConnectionHandler.closeConnection;
import static nextstep.repository.ConnectionHandler.getConnection;

public class ThemeH2Repository implements ThemeRepository {

    @Override
    public void add(Theme theme) {
        Connection con = getConnection();

        try {
            String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    public Theme findById(Long id) {
        Connection con = getConnection();
        Theme result;

        try {
            String sql = "SELECT * FROM theme WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Long themeId = rs.getLong("id");
                String name = rs.getString(2);
                String desc = rs.getString(3);
                Integer price = rs.getInt(4);
                result = new Theme(themeId, name, desc, price);
            } else {
                throw new ThemeNotFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(con);
        }

        return result;
    }

    @Override
    public List<Theme> findAll() {
        Connection con = getConnection();
        List<Theme> result = new ArrayList<>();

        try {
            String sql = "SELECT * FROM theme";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Long themeId = rs.getLong("id");
                String name = rs.getString(2);
                String desc = rs.getString(3);
                Integer price = rs.getInt(4);
                result.add(new Theme(themeId, name, desc, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(con);
        }

        return result;
    }

    @Override
    public void update(Long id, Theme theme) {
        Connection con = getConnection();

        try {
            String sql = "UPDATE theme SET name = ?, SET desc = ?, SET price = ? WHERE id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            ps.setLong(4, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    public void deleteById(Long id) {
        Connection con = getConnection();

        try {
            String sql = "DELETE FROM theme WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(con);
        }
    }
}
