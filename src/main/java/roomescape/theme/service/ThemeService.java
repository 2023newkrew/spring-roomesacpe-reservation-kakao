package roomescape.theme.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.theme.domain.Theme;
import roomescape.theme.dto.request.ThemeRequestDTO;
import roomescape.theme.dto.response.ThemeResponseDTO;
import roomescape.theme.exception.DuplicatedThemeException;
import roomescape.theme.repository.ThemeRepository;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(final ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

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
}
