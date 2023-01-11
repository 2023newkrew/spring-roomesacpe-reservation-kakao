package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ThemeRequestDto;
import nextstep.reservation.repository.ThemeRepository;
import nextstep.reservation.util.mapper.ThemeMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;

    private final ThemeMapper themeMapper;

    public Long addTheme(final ThemeRequestDto themeRequestDto) {
        return themeRepository.add(themeMapper.requestDtoToTheme(themeRequestDto));
    }
}
