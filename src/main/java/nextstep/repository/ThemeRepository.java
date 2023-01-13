package nextstep.repository;

import java.util.Optional;
import nextstep.dto.ThemeCreateDto;
import nextstep.dto.ThemeEditDto;
import nextstep.entity.Theme;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository {
    Theme save(ThemeCreateDto themeCreateDto);

    Optional<Theme> findById(Long id);

    int update(ThemeEditDto themeEditDto);

    void deleteById(Long id);


}
