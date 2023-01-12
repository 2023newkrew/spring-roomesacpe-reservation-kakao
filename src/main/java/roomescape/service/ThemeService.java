package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.dto.ThemeRequest;
import roomescape.repository.ThemeRepository;

import java.util.NoSuchElementException;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme createTheme(ThemeRequest themeRequest) {
        Long id =  themeRepository.createTheme(new Theme(
                null,
                themeRequest.getName(),
                themeRequest.getDesc(),
                themeRequest.getPrice()
                )
        );
        return themeRepository.findThemeById(id);
    }

    public Theme showTheme(Long id) {
        Theme theme = themeRepository.findThemeById(id);
        if (theme == null) {
            throw new NoSuchElementException("없는 테마 조회");
        }
        return themeRepository.findThemeById(id);
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
