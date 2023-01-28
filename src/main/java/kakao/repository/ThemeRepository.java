package kakao.repository;

import kakao.controller.request.ThemeRequest;
import kakao.controller.response.ThemeResponse;
import kakao.model.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {
    public static final String TABLE_NAME = "theme";
    Long create(ThemeRequest themeRequest);
    Optional<Theme> findById(Long id);
    Optional<Theme> findByName(String name);
    List<Theme> findAll();

    void deleteById(Long id);
}
