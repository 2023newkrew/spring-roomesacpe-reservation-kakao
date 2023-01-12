package reservation.respository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import reservation.model.domain.Reservation;
import reservation.model.domain.Theme;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ThemeJdbcTemplateRepository implements ThemeRepository{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ThemeJdbcTemplateRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("THEME")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(Theme theme){
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", theme.getName())
                .addValue("desc", theme.getDesc())
                .addValue("price", theme.getPrice());
        return this.jdbcInsert.executeAndReturnKey(params).longValue();
    }

    @Override
    public List<Theme> findAll(){
        String sql = "SELECT id, name, desc, price theme_id FROM theme";
        try {
            return this.jdbcTemplate.query(sql,
                    (rs, rowNum) -> new Theme(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("desc"),
                        rs.getInt("price")
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(Long themeId) {
        String sql = "DELETE FROM theme WHERE id = ?";
        return this.jdbcTemplate.update(sql, themeId);
    }

    public boolean checkDuplicateName(String name){
        String sql = "SELECT EXISTS (SELECT 1 FROM theme WHERE name = ?)";
        return Boolean.TRUE.equals(this.jdbcTemplate.queryForObject(sql, Boolean.class, name));
    }

    public boolean checkExistById(Long id){
        String sql = "SELECT EXISTS (SELECT 1 FROM theme WHERE id = ?)";
        return Boolean.TRUE.equals(this.jdbcTemplate.queryForObject(sql, Boolean.class, id));
    }
}
