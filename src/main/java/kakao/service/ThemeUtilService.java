package kakao.service;

import java.util.Objects;
import kakao.domain.Theme;
import kakao.error.ErrorCode;
import kakao.error.exception.RoomReservationException;
import kakao.repository.theme.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeUtilService {
    private final ThemeRepository themeRepository;

    public Theme getThemeById(Long id) {
        Theme theme = themeRepository.findById(id);
        if (Objects.isNull(theme)) {
            throw new RoomReservationException(ErrorCode.THEME_NOT_FOUND);
        }
        return theme;
    }
}
