package roomescape.theme.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.theme.domain.Theme;
import roomescape.theme.dto.request.ThemeRequestDTO;
import roomescape.theme.dto.response.ThemeResponseDTO;
import roomescape.theme.exception.AlreadyReservedThemeException;
import roomescape.theme.exception.DuplicatedThemeException;
import roomescape.theme.exception.NoSuchThemeException;
import roomescape.theme.repository.ThemeRepository;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    private final ReservationRepository reservationRepository;

    public ThemeResponseDTO save(final ThemeRequestDTO themeRequestDTO) {
        this.themeRepository.findByName(themeRequestDTO.getName())
                .ifPresent((e) -> {
                    throw new DuplicatedThemeException();
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
                    throw new AlreadyReservedThemeException();
                });

        boolean deleted = this.themeRepository.deleteById(id);

        if (!deleted) {
            throw new NoSuchThemeException();
        }
    }
}
