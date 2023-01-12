package reservation.respository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import reservation.model.domain.Theme;

import javax.sql.DataSource;

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

    public boolean checkDuplicateName(String name){
        String sql = "SELECT EXISTS (SELECT 1 FROM Theme WHERE name = ?)";
        return Boolean.TRUE.equals(this.jdbcTemplate.queryForObject(sql, Boolean.class, name));
    }
}
