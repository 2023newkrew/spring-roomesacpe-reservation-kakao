package nextstep.console.repository;

import nextstep.domain.Theme;
import nextstep.domain.repository.ThemeRepository;
import nextstep.exception.JdbcException;
import nextstep.utils.JdbcUtils;
import nextstep.web.repository.Queries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsoleThemeRepository implements ThemeRepository {
    @Override
    public Long save(Theme theme) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = JdbcUtils.getConnection();
            pstmt = conn.prepareStatement(Queries.Theme.INSERT_SQL, new String[] {"id"});
            pstmt.setString(1, theme.getName());
            pstmt.setString(2, theme.getDesc());
            pstmt.setInt(3, theme.getPrice());
            pstmt.executeUpdate();

            return JdbcUtils.getGeneratedKey(pstmt);
        } catch (SQLException e) {
            throw new JdbcException(e.getMessage());
        } finally {
            JdbcUtils.close(pstmt, conn);
        }
    }

    @Override
    public Optional<Theme> findThemeById(Long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = JdbcUtils.getConnection();
            pstmt = conn.prepareStatement(Queries.Theme.SELECT_BY_ID_SQL);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Theme theme = new Theme(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("desc"),
                        rs.getInt("price")
                );
                return Optional.of(theme);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            JdbcUtils.close(rs, pstmt, conn);
        }

        return Optional.empty();
    }

    @Override
    public boolean existByThemeName(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();
            pstmt = conn.prepareStatement(Queries.Theme.SELECT_BY_NAME_SQL);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new JdbcException(e.getMessage());
        } finally {
            JdbcUtils.close(rs, pstmt, conn);
        }
    }

    @Override
    public List<Theme> getAllThemes() {
        List<Theme> themes = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();
            pstmt = conn.prepareStatement(Queries.Theme.SELECT_ALL_SQL);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Theme theme = new Theme(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("desc"),
                        rs.getInt("price")
                );
                themes.add(theme);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.close(rs, pstmt, conn);
        }
        return themes;
    }

    @Override
    public boolean deleteThemeById(Long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = JdbcUtils.getConnection();
            pstmt = conn.prepareStatement(Queries.Theme.DELETE_BY_ID_SQL);
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new JdbcException(e.getMessage());
        } finally {
            JdbcUtils.close(pstmt, conn);
        }
    }
}
