package nextstep.reservation.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.request.ThemeRequestDto;
import nextstep.reservation.dto.response.ThemeResponseDto;
import nextstep.reservation.exceptions.exception.NotFoundObjectException;
import nextstep.reservation.repository.theme.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    public Long addTheme(ThemeRequestDto themeRequestDto) {
        return themeRepository.add(themeRequestDto.toEntity());
    }

    public ThemeResponseDto getTheme(Long id) {
        return new ThemeResponseDto(
                themeRepository.findById(id).orElseThrow(NotFoundObjectException::new)
        );
    }

    public List<ThemeResponseDto> getAllTheme() {
        return themeRepository.findAll()
                .stream()
                .map(ThemeResponseDto::new)
                .collect(Collectors.toList());
    }

    public void deleteTheme(Long id) {
        boolean result = themeRepository.delete(id);
        if (!result) {
            throw new NotFoundObjectException();
        }
    }

}
