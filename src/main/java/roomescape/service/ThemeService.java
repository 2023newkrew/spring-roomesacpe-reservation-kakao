package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Theme;
import roomescape.dto.ThemeRequest;
import roomescape.dto.ThemeResponse;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.mapper.ThemeMapper;
import roomescape.repository.ReservationWebRepository;
import roomescape.repository.ThemeWebRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeWebRepository themeWebRepository;
    private final ReservationWebRepository reservationWebRepository;

    @Transactional
    public Long createTheme(ThemeRequest themeRequest) {
        return themeWebRepository.save(
                ThemeMapper.INSTANCE.themeRequestToTheme(themeRequest)
        );
    }

    @Transactional
    public ThemeResponse getTheme(Long id){
        Theme theme = themeWebRepository.findOne(id)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.THEME_NOT_FOUND));

        return ThemeMapper.INSTANCE.themeToThemeResponse(theme);
    }

    @Transactional
    public List<ThemeResponse> getThemes() {
        return themeWebRepository.findAll().stream()
                .map(ThemeMapper.INSTANCE::themeToThemeResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateTheme(Long id, ThemeRequest themeRequest) {
        checkThemeExistence(id);
        checkThemeReferencedByReservation(id);

        themeWebRepository.update(
                id,
                ThemeMapper.INSTANCE.themeRequestToTheme(themeRequest)
        );
    }

    private void checkThemeExistence(Long theme_id){
        themeWebRepository.findOne(theme_id)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.THEME_NOT_FOUND));
    }

    private void checkThemeReferencedByReservation(Long id){
        if(reservationWebRepository.findAllByTheme_id(id).size() > 0){
            throw new RoomEscapeException(ErrorCode.THEME_HAS_RESERVATION);
        }
    }

    @Transactional
    public void deleteTheme(Long id){
        checkThemeReferencedByReservation(id);
        themeWebRepository.delete(id);
    }
}
