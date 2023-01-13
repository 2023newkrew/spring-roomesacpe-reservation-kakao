package nextstep.reservation.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ThemeRequestDto;
import nextstep.reservation.dto.ThemeResponseDto;
import nextstep.reservation.exceptions.exception.NotObjectFoundException;
import nextstep.reservation.repository.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository repository;

    public Long addTheme(ThemeRequestDto themeRequestDto) {
        return repository.add(themeRequestDto.toEntity());
    }

    public ThemeResponseDto getTheme(Long id) {
        return new ThemeResponseDto(
                repository.findById(id).orElseThrow(NotObjectFoundException::new)
        );
    }

    public List<ThemeResponseDto> getAllTheme() {
        return repository.findAll()
                .stream()
                .map(ThemeResponseDto::new)
                .collect(Collectors.toList());
    }

    public void deleteTheme(Long id) {
        boolean result = repository.delete(id);
        if (!result) {
            throw new NotObjectFoundException();
        }
    }

}
