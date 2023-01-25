package nextstep.repository;

import nextstep.domain.Theme;
import nextstep.exception.ReservationNotFoundException;
import nextstep.exception.ThemeNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class ThemeH2JdbcTemplateRepository implements ThemeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ThemeH2JdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Theme theme) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;
        });
    }

    @Override
    public Optional<Theme> findById(Long id) {
        String sql = "SELECT * FROM theme where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    sql,
                    (resultSet, rowNum) -> new Theme(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4)),
                id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Theme> findAll() {
        String sql = "SELECT * FROM theme";
        try {
            return jdbcTemplate.query(
                    sql,
                    (resultSet, rowNum) -> new Theme(
                            resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4))
                    );
        } catch (EmptyResultDataAccessException e) {
            throw new ThemeNotFoundException();
        }
    }

    @Override
    public void update(Long id, Theme theme) {
        String sql = "UPDATE theme SET name = ?, `desc` = ?, price = ? WHERE id = ?;";
        jdbcTemplate.update(
                sql, theme.getName(), theme.getDesc(), theme.getPrice(), id
        );
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM theme where id = ?";
        int updateCount = jdbcTemplate.update(sql, id);
        if (updateCount == 0) {
            throw new ReservationNotFoundException();
        }
    }
}
