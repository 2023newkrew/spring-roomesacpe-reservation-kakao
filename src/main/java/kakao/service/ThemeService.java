package kakao.service;

import kakao.controller.request.ThemeRequest;
import kakao.controller.response.ThemeResponse;

import java.util.List;

public interface ThemeService {
    Long create(ThemeRequest themeRequest);

    List<ThemeResponse> findAll();

    void delete(Long id);
}
