package nextstep.dao;

import nextstep.domain.Theme;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThemeJdbcApiDAO implements ThemeDAO, JdbcApiDAO {
    @Override
    public Long save(Theme theme) {
        try (Connection con = getConnection()) {
            PreparedStatementCreator insertPreparedStatementCreator = ThemeDAO.getInsertPreparedStatementCreator(theme);
            PreparedStatement ps = insertPreparedStatementCreator.createPreparedStatement(con);

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
            return -1L;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Theme> findAll() {
        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement(FIND_ALL_SQL);

            ResultSet rs = ps.executeQuery();
            List<Theme> themes = new ArrayList<>();
            while (rs.next()) {
                themes.add(THEME_ROW_MAPPER.mapRow(rs, rs.getRow()));
            }
            return themes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(Long id) {
        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement(DELETE_BY_ID_SQL);
            ps.setLong(1, id);

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
