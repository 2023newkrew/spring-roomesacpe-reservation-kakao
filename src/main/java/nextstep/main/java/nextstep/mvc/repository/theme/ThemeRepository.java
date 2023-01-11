package nextstep.main.java.nextstep.mvc.repository.theme;

import nextstep.main.java.nextstep.mvc.domain.theme.Theme;
import nextstep.main.java.nextstep.mvc.domain.theme.request.ThemeCreateRequest;
import nextstep.main.java.nextstep.mvc.domain.theme.request.ThemeUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {
    Long save(ThemeCreateRequest request);
    Optional<Theme> findById(long id);
    List<Theme> findAll();
    void deleteById(long id);
    void update(Long id, ThemeUpdateRequest request);
}
