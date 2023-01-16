package web.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import web.domain.Theme;
import web.dto.request.ThemeRequestDTO;
import web.dto.response.ThemeIdDto;
import web.dto.response.ThemeResponseDTO;
import web.exception.NoSuchThemeException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ThemeJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public ThemeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> findAllThemeByName(Theme theme) {
        String selectSql = "SELECT id FROM theme WHERE name = (?) LIMIT 1 ";

        return jdbcTemplate.query(selectSql, ((rs, rowNum) ->
                rs.getLong("id")), String.valueOf(theme.getName()));

    }

    public ThemeIdDto createTheme(Theme theme) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, String.valueOf(theme.getName()));
            ps.setString(2, String.valueOf(theme.getDesc()));
            ps.setInt(3, theme.getPrice());
            return ps;
        }, keyHolder);

        return new ThemeIdDto((Long) keyHolder.getKey());
    }

    public List<ThemeResponseDTO> findAllThemes() {
        String sql = "SELECT * FROM theme";

        return jdbcTemplate.query(sql, ((rs, rowNum) -> new ThemeResponseDTO(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("desc"),
                rs.getInt("price")
                )));
    }

    public Theme findThemeById(final Long id) throws NoSuchThemeException {
        String sql = "SELECT * FROM theme WHERE id = (?) LIMIT 1 ";

        List<Theme> themes =  jdbcTemplate.query(sql, ((rs, rowNum) -> new Theme(
                rs.getString("name"),
                rs.getString("desc"),
                rs.getInt("price")
        )), id);

        if (themes.size() == 0) {
            throw new NoSuchThemeException();
        }
        return themes.get(0);
    }

    public void updateTheme(Long themeId, ThemeRequestDTO themeRequestDTO) {
        String sql = "UPDATE theme set name = (?), desc = (?), price = (?) WHERE id = (?)";

        jdbcTemplate.update(sql,
                themeRequestDTO.getName(),
                themeRequestDTO.getDesc(),
                themeRequestDTO.getPrice(),
                themeId
        );

    }
}
