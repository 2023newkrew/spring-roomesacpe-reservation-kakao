package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ThemeRequest;
import nextstep.reservation.dto.ThemeResponse;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.exception.RoomEscapeException;
import nextstep.reservation.repository.ThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static nextstep.reservation.constant.RoomEscapeConstant.ENTITY_DELETE_NUMBER;
import static nextstep.reservation.exception.RoomEscapeExceptionCode.NO_SUCH_THEME;

@Service
@RequiredArgsConstructor
@Transactional
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeResponse registerTheme(ThemeRequest themeRequest) {
        Theme registeredTheme = themeRepository.save(themeRequest.toEntityWithDummyId());
        return ThemeResponse.from(registeredTheme);
    }

    @Transactional(readOnly = true)
    public ThemeResponse findById(long id) {
        Theme foundedTheme = themeRepository.findById(id)
                .orElseThrow(() -> new RoomEscapeException(NO_SUCH_THEME));
        return ThemeResponse.from(foundedTheme);
    }

    @Transactional(readOnly = true)
    public List<ThemeResponse> findAll() {
        List<Theme> allThemes = themeRepository.findAll();
        return allThemes.stream()
                .map(ThemeResponse::from)
                .collect(Collectors.toList());
    }

    public boolean deleteById(long id) {
        int deletedRowNumber = themeRepository.deleteById(id);
        return deletedRowNumber == ENTITY_DELETE_NUMBER;
    }

    public void clear() {
        themeRepository.clear();
    }

}
