package nextstep.repository;

import nextstep.Theme;

import java.util.List;

public interface ThemeRepository {
    Long insert(Theme theme);

    List<Theme> getList();

    boolean deleteById(Long id);
}
