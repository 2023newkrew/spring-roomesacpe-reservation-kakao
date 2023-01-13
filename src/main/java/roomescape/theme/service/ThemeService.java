package roomescape.theme.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.theme.dto.ThemeRequestDto;
import roomescape.theme.dto.ThemeResponseDto;

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
