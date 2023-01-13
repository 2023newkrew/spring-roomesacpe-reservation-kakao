package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.dto.ThemeCreateRequest;
import roomescape.dto.ThemeShowResponse;
import roomescape.repository.ThemeRepository;

import java.util.NoSuchElementException;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme createTheme(ThemeCreateRequest themeCreateRequest) {
        Long id =  themeRepository.createTheme(new Theme(
                null,
                themeCreateRequest.getName(),
                themeCreateRequest.getDesc(),
                themeCreateRequest.getPrice()
                )
        );
        return themeRepository.findThemeById(id);
    }

    public ThemeShowResponse showTheme(Long id) {
        Theme theme = themeRepository.findThemeById(id);
        if (theme == null) {
            throw new NoSuchElementException("없는 테마 조회");
        }
        return ThemeShowResponse.of(theme);
    }

    public Theme updateTheme(Theme theme) {
        int count = themeRepository.updateTheme(theme);
        if (count == 0) {
            throw new NoSuchElementException("없는 테마 수정 요청");
        }
        return themeRepository.findThemeById(theme.getId());
    }

    public int deleteTheme(Long id) {
        return themeRepository.deleteTheme(id);
    }
}
