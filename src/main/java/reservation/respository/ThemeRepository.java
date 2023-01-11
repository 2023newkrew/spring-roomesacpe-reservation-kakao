package reservation.respository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import reservation.domain.Theme;
import reservation.domain.dto.ThemeDto;

import java.sql.PreparedStatement;
import java.util.Objects;

@Repository
public class ThemeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createTheme(ThemeDto themeDto) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, themeDto.getName());
            ps.setString(2, themeDto.getDesc());
            ps.setInt(3, themeDto.getPrice());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Theme getTheme(Long themeId) {
        String sql = "SELECT id, name, desc, price FROM theme WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Theme(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("desc"),
                    rs.getInt("price")
            ), themeId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void deleteTheme(Long themeId) {
        String sql = "DELETE FROM theme WHERE id = ?";
        jdbcTemplate.update(sql, themeId);
    }

    // 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.
    public boolean existTheme(ThemeDto themeDto) {
        String sql = "SELECT EXISTS(SELECT 1 FROM theme WHERE name = ? AND desc = ? AND price = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, themeDto.getName(), themeDto.getDesc(), themeDto.getPrice()));
    }
}
