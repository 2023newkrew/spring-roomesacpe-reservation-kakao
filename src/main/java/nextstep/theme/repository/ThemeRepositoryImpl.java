package nextstep.theme.repository;

import lombok.RequiredArgsConstructor;
import nextstep.theme.domain.Theme;
import nextstep.theme.repository.jdbc.ThemeResultSetParser;
import nextstep.theme.repository.jdbc.ThemeStatementCreator;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
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
    public Theme insert(Theme theme) throws DuplicateKeyException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> statementCreator.createInsert(connection, theme),
                keyHolder
        );
        theme.setId(keyHolder.getKeyAs(Long.class));

        return theme;
    }

    @Override
    public Theme getById(Long id) {
        return jdbcTemplate.query(
                connection -> statementCreator.createSelectById(connection, id),
                resultSetParser::parseSingleTheme
        );
    }

    @Override
    public List<Theme> getAll() {
        return jdbcTemplate.query(
                statementCreator::createSelectAll,
                resultSetParser::parseAllThemes
        );
    }

    @Override
    public Theme update(Theme theme) throws DuplicateKeyException {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
