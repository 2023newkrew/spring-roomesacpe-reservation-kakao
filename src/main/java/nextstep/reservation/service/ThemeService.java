package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ThemeRequestDto;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.repository.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;

    public Long addTheme(final ThemeRequestDto themeRequestDto) {
        return themeRepository.add(new Theme(themeRequestDto));
    }
}
