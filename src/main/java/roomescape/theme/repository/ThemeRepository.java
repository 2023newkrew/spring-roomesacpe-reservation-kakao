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
    private static final String INSERT_QUERY = "INSERT INTO THEME (name, desc, price) VALUES (?, ?, ?);";
    private static final String SELECT_BY_NAME_QUERY = "SELECT * FROM THEME WHERE name = ?;";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM THEME;";
    private static final String DELETE_QUERY = "DELETE FROM THEME WHERE id = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM THEME WHERE id = ?;";
    final JdbcTemplate jdbcTemplate;
    private final RowMapper<Theme> themeRowMapper =
            (resultSet, rowNum) -> ThemeMapper.mapToTheme(resultSet);

    public ThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(ThemeRequest themeRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_QUERY, new String[]{"id"});
            ps.setString(INDEX_NAME, themeRequest.getName());
            ps.setString(INDEX_DESC, themeRequest.getDesc());
            ps.setInt(INDEX_THEME_NAME, themeRequest.getPrice());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Optional<Theme> findByName(String name) {
        try {
            Theme duplicatedTheme = jdbcTemplate.queryForObject(SELECT_BY_NAME_QUERY, themeRowMapper, name);
            return Optional.ofNullable(duplicatedTheme);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Theme> viewAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, themeRowMapper);
    }

    public void deleteById(Long themeId) {
        jdbcTemplate.update(DELETE_QUERY, themeId);
    }

    public Optional<Theme> findById(Long themeId) {
        try {
            Theme theme = jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, themeRowMapper, themeId);
            return Optional.ofNullable(theme);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
