package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ThemeRequestDto;
import roomescape.dto.ThemeResponseDto;

@Service
public class ThemeService {
    public List<ThemeResponseDto> findAllThemes() {
        return null;
    }

    public ThemeResponseDto findThemeById(Long id) {
        return null;
    }

    public long createTheme(ThemeRequestDto themeRequestDto) {
        return 0;
    }

    public void removeThemeById(Long id) {
    }
}
