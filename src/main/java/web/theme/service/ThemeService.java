package web.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.theme.dto.ThemeRequestDto;
import web.theme.dto.ThemeResponseDto;
import web.theme.repository.ThemeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    public long save(ThemeRequestDto themeRequestDto) {
        return themeRepository.save(themeRequestDto.toEntity());
    }

    public List<ThemeResponseDto> findThemes() {
        return themeRepository.findAll()
                .stream()
                .map(ThemeResponseDto::of)
                .collect(Collectors.toList());
    }

    public void deleteById(long themeId) {
        themeRepository.delete(themeId);
    }
}
