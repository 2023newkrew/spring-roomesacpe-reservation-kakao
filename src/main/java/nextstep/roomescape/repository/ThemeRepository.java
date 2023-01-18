package nextstep.roomescape.repository;

import nextstep.roomescape.repository.model.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;


@Repository
public class ThemeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Theme> rowMapper = (resultSet, rowNum) -> new Theme(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("desc"),
            resultSet.getInt("price")
    );

    public Long create(Theme theme) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Theme> findAll() {
        String sql = "SELECT id, name, desc, price from theme;";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Theme findByName(String name) {
        String sql = "select id, name, desc, price from theme where name=?";
        return jdbcTemplate.queryForObject(sql, rowMapper, name);
    }

    public Theme find(Theme theme) {
        String sql = "select id, name, desc, price from theme where id=? and name=? and desc=? and price=?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, theme.getId(), theme.getName(), theme.getDesc(), theme.getPrice());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Theme findById(Long id) {
        String sql = "select id, name, desc, price from theme where id=?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int updateById(Long id, Theme theme) {
        String sql = "update theme set name=?, desc=?, price=?" +
                "where id = ?";
        return jdbcTemplate.update(sql, theme.getName(), theme.getDesc(), theme.getPrice(), id);
    }

    public void delete(Long id) {
        String sql = "delete from theme where id = ?";
        jdbcTemplate.update(sql, id);
    }


}
