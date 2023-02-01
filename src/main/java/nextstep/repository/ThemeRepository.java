package nextstep.repository;

import nextstep.domain.Theme;

import java.util.List;

public interface ThemeRepository {
    Long insert(Theme theme);

    List<Theme> getList();

    boolean deleteById(Long id);
}
