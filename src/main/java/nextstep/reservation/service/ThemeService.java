package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ThemeRequestDto;
import nextstep.reservation.dto.ThemeResponseDto;
import nextstep.reservation.repository.ThemeRepository;
import nextstep.reservation.util.mapper.ThemeMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;

    private final ThemeMapper themeMapper;

    public Long addTheme(final ThemeRequestDto themeRequestDto) {
        return themeRepository.add(themeMapper.requestDtoToTheme(themeRequestDto));
    }

    public List<ThemeResponseDto> getAllThemes() {
        return themeRepository.findAll()
                .stream()
                .map(themeMapper::themeToThemeResponseDto)
                .collect(Collectors.toList());
    }

    public void deleteTheme(final Long id) {
        themeRepository.delete(id);
    }
}
