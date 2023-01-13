package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Theme;
import roomescape.dto.ThemeRequest;
import roomescape.dto.ThemeResponse;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.mapper.ThemeMapper;
import roomescape.repository.ThemeRepository;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(
            ThemeRepository themeRepository
    ){
        this.themeRepository = themeRepository;
    }

    @Transactional
    public Long createTheme(ThemeRequest themeRequest) {
        return themeRepository.save(
                ThemeMapper.INSTANCE.themeRequestToTheme(themeRequest)
        );
    }

    @Transactional
    public ThemeResponse getTheme(Long id){
        Theme theme = themeRepository.findOne(id)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.THEME_NOT_FOUND));

        return ThemeMapper.INSTANCE.themeToThemeResponse(theme);
    }

    @Transactional
    public void deleteTheme(Long id){
        themeRepository.delete(id);
    }
}
