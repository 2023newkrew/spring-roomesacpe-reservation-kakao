package web.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.theme.dto.ThemeRequestDto;
import web.theme.repository.ThemeRepository;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    public long save(ThemeRequestDto themeRequestDto) {
        return themeRepository.save(themeRequestDto.toEntity());
    }
}
