package roomescape.theme.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.common.exception.ErrorCode;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.theme.dto.request.ThemeRequestDTO;
import roomescape.theme.dto.response.ThemeResponseDTO;
import roomescape.theme.entity.Theme;
import roomescape.theme.exception.ThemeException;
import roomescape.theme.repository.ThemeRepository;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    private final ReservationRepository reservationRepository;

    public ThemeResponseDTO save(final ThemeRequestDTO themeRequestDTO) {
        this.themeRepository.findByName(themeRequestDTO.getName())
                .ifPresent((e) -> {
                    throw new ThemeException(ErrorCode.DUPLICATED_THEME);
                });

        final Theme theme = themeRequestDTO.toEntity();

        this.themeRepository.save(theme);

        return ThemeResponseDTO.from(theme);
    }

    public List<ThemeResponseDTO> findAll() {
        return this.themeRepository.findAll().stream()
                .map(ThemeResponseDTO::from)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        this.reservationRepository.findByThemeId(id)
                .ifPresent((e) -> {
                    throw new ThemeException(ErrorCode.ALREADY_RESERVED_THEME);
                });

        boolean deleted = this.themeRepository.deleteById(id);

        if (!deleted) {
            throw new ThemeException(ErrorCode.NO_SUCH_THEME);
        }
    }
}
