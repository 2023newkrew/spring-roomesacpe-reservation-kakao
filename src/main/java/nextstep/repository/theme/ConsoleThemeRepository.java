package nextstep.repository.theme;

import nextstep.domain.reservation.Reservation;
import nextstep.domain.theme.Theme;
import nextstep.repository.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsoleThemeRepository implements ThemeRepository {

    @Override
    public long add(Theme theme) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Long id = null;

        try {
            String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";
            con = ConnectionManager.getConnection();
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
            ConnectionManager.closeAll(rs, ps, con);
        }

        return id;
    }

    @Override
    public List<Theme> findAll() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Theme> themeList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM theme;";
            con = ConnectionManager.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                themeList.add(new Theme(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4)
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeAll(rs, ps, con);
        }

        return themeList;
    }

    @Override
    public int update(Theme theme) {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;

        try {
            String sql = "UPDATE THEME SET name = ?, desc = ?, price = ? WHERE id = ?;";
            con = ConnectionManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            ps.setLong(4, theme.getId());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeAll(ps, con);
        }

        return result;
    }

    @Override
    public int delete(long id) {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;

        try {
            String sql = "DELETE FROM theme WHERE id = ?;";
            con = ConnectionManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeAll(ps, con);
        }

        return result;
    }
}
