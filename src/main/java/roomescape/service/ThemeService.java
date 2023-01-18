package roomescape.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import roomescape.dto.ThemeRequestDto;
import roomescape.dto.ThemeResponseDto;
import roomescape.dto.ThemesResponseDto;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.model.Theme;
import roomescape.repository.ThemeJdbcRepository;
import roomescape.repository.ThemeRepository;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeJdbcRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public ThemeResponseDto createTheme(ThemeRequestDto req) {
        Theme theme = new Theme(null, req.getName(), req.getDesc(), req.getPrice());
        try {
            theme = themeRepository.save(theme);
        } catch (DuplicateKeyException e) {
            throw new RoomEscapeException(ErrorCode.THEME_NAME_ALREADY_EXISTS);
        }
        return new ThemeResponseDto(theme);
    }

    public ThemesResponseDto findThemes() {
        List<Theme> themes = getThemes();
        return new ThemesResponseDto(themes);
    }

    public void deleteTheme(Long id) {
        Boolean isDeleted = themeRepository.delete(id);
        if (!isDeleted) {
            throw new RoomEscapeException(ErrorCode.NO_SUCH_ELEMENT);
        }
    }

    Theme getTheme(Long id) {
        return themeRepository.find(id).orElseThrow(() -> {
            throw new RoomEscapeException(ErrorCode.NO_SUCH_ELEMENT);
        });
    }

    List<Theme> getThemes() {
        return themeRepository.findAll();
    }
}
