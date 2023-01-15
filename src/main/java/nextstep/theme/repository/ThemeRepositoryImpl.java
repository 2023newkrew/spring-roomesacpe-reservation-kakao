package nextstep.theme.repository;

import lombok.RequiredArgsConstructor;
import nextstep.theme.domain.Theme;
import nextstep.theme.repository.jdbc.ThemeResultSetParser;
import nextstep.theme.repository.jdbc.ThemeStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
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
        return null;
    }

    @Override
    public Theme getById(Long id) {
        return null;
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
