package nextstep.dao;

import nextstep.domain.Theme;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.util.List;

public abstract class ThemeDAO {
    protected String INSERT_SQL = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?)";
    protected String FIND_ALL_SQL = "SELECT * FROM theme";
    protected String DELETE_BY_ID_SQL = "DELETE FROM theme WHERE id = ?";

    protected RowMapper<Theme> THEME_ROW_MAPPER = (resultSet, rowNum) -> new Theme(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("desc"),
            resultSet.getInt("price")
    );

    public abstract Long save(Theme theme);

    public abstract List<Theme> findAll();

    public abstract int deleteById(Long id);

    protected PreparedStatementCreator getInsertPreparedStatementCreator(Theme theme) {
        return connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;
        };
    }
}
