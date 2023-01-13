package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ThemeRequest;
import nextstep.reservation.dto.ThemeResponse;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.exception.ReservationException;
import nextstep.reservation.repository.ThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static nextstep.reservation.exception.ReservationExceptionCode.NO_SUCH_THEME;

@Service
@RequiredArgsConstructor
@Transactional
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeResponse registerTheme(ThemeRequest themeRequest) {
        Theme registeredTheme = themeRepository.save(themeRequest.toEntity());
        return ThemeResponse.from(registeredTheme);
    }

    @Transactional(readOnly = true)
    public ThemeResponse findById(long id) {
        Optional<Theme> foundedThemeOptional = themeRepository.findById(id);
        if (foundedThemeOptional.isEmpty()) {
            throw new ReservationException(NO_SUCH_THEME);
        }
        Theme foundedTheme = foundedThemeOptional.get();
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
        return deletedRowNumber == 1;
    }

    public void clear() {
        themeRepository.clear();
    }

}
