package nextstep.theme.repository;

import lombok.RequiredArgsConstructor;
import nextstep.theme.domain.Theme;
import nextstep.theme.repository.jdbc.ThemeResultSetParser;
import nextstep.theme.repository.jdbc.ThemeStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ThemeRepositoryImpl implements ThemeRepository {

    private final JdbcTemplate jdbcTemplate;

    private final ThemeStatementCreator statementCreator;

    private final ThemeResultSetParser resultSetParser;


    @Override
    public Theme insert(Theme theme) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(getInsertStatementCreator(theme), keyHolder);
        theme.setId(keyHolder.getKeyAs(Long.class));

        return theme;
    }

    private PreparedStatementCreator getInsertStatementCreator(Theme theme) {
        return connection -> statementCreator.createInsert(connection, theme);
    }

    @Override
    public Theme getById(Long id) {
        return jdbcTemplate.query(
                getSelectByIdStatementCreator(id),
                resultSetParser::parseTheme
        );
    }

    private PreparedStatementCreator getSelectByIdStatementCreator(Long id) {
        return connection -> statementCreator.createSelectById(connection, id);
    }

    @Override
    public List<Theme> getAll() {
        return null;
    }

    @Override
    public Theme update(Theme theme) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
