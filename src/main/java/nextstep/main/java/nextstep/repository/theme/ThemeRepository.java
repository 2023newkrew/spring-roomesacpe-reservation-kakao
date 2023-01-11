package nextstep.main.java.nextstep.repository.theme;

import nextstep.main.java.nextstep.domain.theme.Theme;
import nextstep.main.java.nextstep.domain.theme.ThemeCreateRequestDto;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ThemeRepository {
    Long save(ThemeCreateRequestDto request);
    Optional<Theme> findById(long id);

    Optional<Theme> findByName(String name);

    List<Theme> findAll();

    void deleteById(long id);
}
