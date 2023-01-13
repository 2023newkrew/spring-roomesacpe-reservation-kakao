package kakao.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import kakao.domain.Theme;
import kakao.dto.request.CreateThemeRequest;
import kakao.dto.response.ThemeResponse;
import kakao.error.ErrorCode;
import kakao.error.exception.RoomReservationException;
import kakao.repository.theme.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeResponse createTheme(CreateThemeRequest request) {
        Theme theme = Theme.builder()
                .name(request.getName())
                .desc(request.getDesc())
                .price(request.getPrice())
                .build();
        return new ThemeResponse(themeRepository.save(theme));
    }

    @Transactional(readOnly = true)
    public List<ThemeResponse> getThemes() {
        return themeRepository.findAll().stream().map(ThemeResponse::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ThemeResponse getTheme(Long id) {
        Theme theme = themeRepository.findById(id);
        if (Objects.isNull(theme)) {
            throw new RoomReservationException(ErrorCode.THEME_NOT_FOUND);
        }
        return new ThemeResponse(theme);
    }

    public int deleteTheme(Long id) {
        return themeRepository.delete(id);
    }
}
