package nextstep.repository.theme;

import nextstep.domain.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcThemeRepository implements ThemeRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private final RowMapper<Theme> themeActorRowMapper = (resultSet, rowNum) ->
            from(resultSet);

    @Override
    public Theme findByThemeId(Long id) {
        return jdbcTemplate.queryForObject(findSql, themeActorRowMapper, id);
    }

    @Override
    public List<Theme> findAll() {
        return jdbcTemplate.query(
                findAllSql,
                (resultSet, rowNum) -> from(resultSet));
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(deleteByIdSql, id);
    }

    @Override
    public Long save(Theme theme) {
        validateTheme(theme);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = (connection) ->
                getReservationPreparedStatement(connection, theme);

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Theme findByTheme(Theme theme) {
        try {
            return jdbcTemplate.queryForObject(findByThemeSql, themeActorRowMapper, theme.getName(), theme.getDesc(), theme.getPrice());
        }catch (Exception e){
            throw new RuntimeException("테마를 찾을 수 없습니다.");
        }
    }

    @Override
    public void dropThemeTable() {
        try {
            jdbcTemplate.execute(dropTableSql);
        } catch (Exception e){
            throw new RuntimeException("테이블 삭제 오류");
        }
    }

    @Override
    public void createThemeTable() {
        try {
            jdbcTemplate.execute(createTableSql);
        } catch (Exception e){
            throw new RuntimeException("테이블 생성 오류");
        }
    }

    private void validateTheme(Theme theme) {
        Integer count = jdbcTemplate.queryForObject(checkDuplicationSql, Integer.class, theme.getName());
        if (count > 0) throw new RuntimeException("이름이 같은 테마가 이미 존재합니다.");
    }
}
