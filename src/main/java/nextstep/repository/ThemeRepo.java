package nextstep.repository;

import nextstep.domain.theme.Theme;

import java.util.List;

public interface ThemeRepo {
    public long save(Theme theme);

    public int delete(long id);

    public Theme findById(long id);

    public List<Theme> findAll();
}
