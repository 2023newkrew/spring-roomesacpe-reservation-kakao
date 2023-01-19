package nextstep.console.repository;

import nextstep.domain.Theme;
import nextstep.domain.repository.ThemeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nextstep.console.util.DBConnectionUtil.*;

public class ThemeConnRepository implements ThemeRepository {
    @Override
    public Theme create(Theme theme) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();

            String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";
            pstmt = conn.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, theme.getName());
            pstmt.setString(2, theme.getDesc());
            pstmt.setInt(3, theme.getPrice());
            pstmt.executeUpdate();

            resultSet = pstmt.getGeneratedKeys();
            resultSet.next();
            long id = resultSet.getLong("id");
            theme.setId(id);
            return theme;
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            closeOperation(conn, pstmt, resultSet);
        }
    }

    @Override
    public Theme find(long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM theme WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return new Theme(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("desc"),
                        resultSet.getInt("price")
                );
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            closeOperation(conn, pstmt, resultSet);
        }
        return null;
    }

    @Override
    public List<Theme> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM theme";
            pstmt = conn.prepareStatement(sql);

            resultSet = pstmt.executeQuery();
            List<Theme> themes = new ArrayList<>();
            while (resultSet.next()) {
                themes.add(new Theme(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("desc"),
                        resultSet.getInt("price")
                ));
            }
            return themes;
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            closeOperation(conn, pstmt, resultSet);
        }
    }

    @Override
    public boolean delete(long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();

            String sql = "DELETE FROM theme WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            closeOperation(conn, pstmt, resultSet);
        }
    }
}
