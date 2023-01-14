package nextstep.repository;

import nextstep.domain.Theme;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public interface ThemeDao {
    default RowMapper<Theme> getRowMapper() {
        return (resultSet, rowNum) -> new Theme(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("desc"),
                resultSet.getInt("price")
        );
    }

    default PreparedStatementCreator getPreparedStatementCreatorForSave(Theme theme) {
        return (connection) -> {
            final String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;
        };
    }

    default PreparedStatementCreator getPreparedStatementCreatorForUpdate(Theme theme) {
        return (connection) -> {
            final String sql = "UPDATE theme SET name = ?, desc = ?, price = ? WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            ps.setLong(4, theme.getId());
            return ps;
        };
    }

    Long save(Theme theme);

    Optional<Theme> findByName(String name);

    Optional<Theme> findById(Long id);

    List<Theme> findAll();

    void update(Theme theme);

    void delete(Long id);
}
