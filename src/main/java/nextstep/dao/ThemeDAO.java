package nextstep.dao;

import nextstep.domain.Theme;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.util.List;

public interface ThemeDAO {
    String INSERT_SQL = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?)";
    String FIND_ALL_SQL = "SELECT * FROM theme";
    String DELETE_BY_ID_SQL = "DELETE FROM theme WHERE id = ?";

    RowMapper<Theme> THEME_ROW_MAPPER = (resultSet, rowNum) -> new Theme(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("desc"),
            resultSet.getInt("price")
    );

    Long save(Theme theme);

    List<Theme> findAll();

    int deleteById(Long id);

    static PreparedStatementCreator getInsertPreparedStatementCreator(Theme theme) {
        return connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;
        };
    }
}
