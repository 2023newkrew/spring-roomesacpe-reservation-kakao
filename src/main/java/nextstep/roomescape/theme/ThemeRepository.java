package nextstep.roomescape.theme;

import nextstep.roomescape.theme.domain.entity.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class ThemeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ThemeRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Theme create(Theme theme){
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate).withTableName("theme").usingGeneratedKeyColumns("id");
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(theme);

        Long id = insertActor.executeAndReturnKey(parameters).longValue();
        return new Theme(id, theme.getName(), theme.getDesc(),theme.getPrice());
    }

    public List<Theme> findAll(){
        String sql = "select id, name, desc, price from theme";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> {
                    Theme theme = Theme.builder()
                            .id(rs.getLong("id"))
                            .name(rs.getString("name"))
                            .desc(rs.getString("desc"))
                            .price(rs.getInt("price"))
                            .build();
                    return theme;
                });
    }

    public Optional<Theme> findByName(String name){
        String sql = "select id, name, desc, price from theme where name=?";
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> {
                        Theme theme = Theme.builder()
                                .id(rs.getLong("id"))
                                .name(rs.getString("name"))
                                .desc(rs.getString("desc"))
                                .price(rs.getInt("price"))
                                .build();
                        return theme;
                    }, name));
    }

    public Boolean delete(Long id){
        String sql = "delete from theme where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id)) == 1 ;
    }


}
