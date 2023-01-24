package roomescape.theme.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;
import roomescape.reservation.exception.DuplicatedReservationException;
import roomescape.theme.dto.ThemeRequest;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ThemeRepository {

    private static final int INDEX_NAME = 1;
    private static final int INDEX_DESC = 2;
    private static final int INDEX_THEME_NAME = 3;
    final JdbcTemplate jdbcTemplate;
    private final RowMapper<Theme> themeRowMapper =
            (resultSet, rowNum) -> ThemeMapper.mapToTheme(resultSet);

    public ThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(ThemeRequest themeRequest) {
        String sql = "INSERT INTO THEME (name, desc, price) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(INDEX_NAME, themeRequest.getName());
            ps.setString(INDEX_DESC, themeRequest.getDesc());
            ps.setInt(INDEX_THEME_NAME, themeRequest.getPrice());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Optional<Theme> findByName(String name) {
        String sql = "SELECT * FROM THEME WHERE name = ?;";
        try {
            Theme duplicatedTheme = jdbcTemplate.queryForObject(sql, themeRowMapper, name);
            return Optional.ofNullable(duplicatedTheme);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Theme> viewAll() {
        String sql = "SELECT * FROM THEME;";
        return jdbcTemplate.query(sql, themeRowMapper);
    }

    public void deleteById(Long themeId) {
        String sql = "DELETE FROM THEME WHERE id = ?";
        jdbcTemplate.update(sql, themeId);
    }

    public Optional<Theme> findById(Long themeId) {
        String sql = "SELECT * FROM THEME WHERE id = ?;";
        try {
            Theme theme = jdbcTemplate.queryForObject(sql, themeRowMapper, themeId);
            return Optional.ofNullable(theme);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
