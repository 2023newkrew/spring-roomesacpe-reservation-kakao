package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Theme;
import roomescape.dto.ThemeCreateRequest;
import roomescape.dto.ThemeShowResponse;
import roomescape.dto.ThemeUpdateRequest;
import roomescape.exception.ThemeNotFoundException;
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
        return themeRepository.findThemeById(id).orElseThrow(ThemeNotFoundException::new);
    }

    public ThemeShowResponse showTheme(Long id) {
        Theme theme = themeRepository.findThemeById(id).orElseThrow(ThemeNotFoundException::new);
        return ThemeShowResponse.of(theme);
    }

    @Transactional
    public ThemeShowResponse updateTheme(ThemeUpdateRequest themeUpdateRequest, Long id) {
        Theme theme = themeRepository.findThemeById(id).orElseThrow(ThemeNotFoundException::new);
        themeUpdateRequest.fill(theme);
        int count = themeRepository.updateTheme(themeUpdateRequest, id);
        if (count == 0) {
            throw new ThemeNotFoundException("없는 테마 수정 요청");
        }
        return ThemeShowResponse.of(themeRepository.findThemeById(id).orElseThrow(ThemeNotFoundException::new));
    }

    public int deleteTheme(Long id) {
        return themeRepository.deleteTheme(id);
    }
}
