package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Theme;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class ThemeWebRepository implements CrudRepository<Theme, Long> {
    private final JdbcTemplate jdbcTemplate;

    public ThemeWebRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Theme> themeRowMapper = (resultSet, rowNum) -> new Theme(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("desc"),
            resultSet.getInt("price")
    );

    @Override
    public Long save(Theme theme) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;
        }, keyHolder);

        return (Long) keyHolder.getKey();
    }

    @Override
    public Optional<Theme> findOne(Long id) {
        String sql = "SELECT * FROM theme WHERE id = (?)";

        return jdbcTemplate.query(sql,themeRowMapper , id)
                .stream()
                .findAny();
    }

    public List<Theme> findAll() {
        String sql = "SELECT * from theme";

        return jdbcTemplate.query(sql, themeRowMapper);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM theme WHERE id = (?)";
        jdbcTemplate.update(sql, id);
    }
}
