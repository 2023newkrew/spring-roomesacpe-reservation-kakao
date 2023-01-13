package roomescape.theme.repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Theme;

@Repository
public class ThemeRepositoryImpl implements ThemeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ThemeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Theme theme) {
        String sql = "insert into theme (name, desc, price) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setString(3, String.valueOf(theme.getPrice()));
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Theme> findById(Long id) {
        String sql = "select * from theme WHERE id = ?";
        Theme theme;
        try {
            theme = jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> Theme.builder()
                    .themeId(id)
                    .name(resultSet.getString("name"))
                    .desc(resultSet.getString("desc"))
                    .price(resultSet.getInt("price"))
                    .build(), id);
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(theme);
    }

    @Override
    public List<Theme> findAll() {
        String sql = "select * from theme";
        try {
            return jdbcTemplate.query(sql, (resultSet, rowNumber) -> Theme.builder()
                    .themeId(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .desc(resultSet.getString("desc"))
                    .price(resultSet.getInt("price"))
                    .build());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public int delete(Long id) {
        String sql = "delete from theme where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public boolean isThemeNameDuplicated(String themeName) {
        String sql = "select count(*) from theme where name =  ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, themeName) > 0;
    }
}
