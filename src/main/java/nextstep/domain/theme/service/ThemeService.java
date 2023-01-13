package nextstep.domain.theme.service;

import nextstep.domain.theme.domain.Theme;
import nextstep.domain.theme.dto.ThemeRequestDto;
import nextstep.domain.theme.repository.ThemeRepository;
import nextstep.global.exceptions.exception.DuplicatedNameThemeException;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Long add(ThemeRequestDto themeRequestDto) {
        if (themeRepository.findByName(themeRequestDto.getName()).isPresent()) {
            throw new DuplicatedNameThemeException();
        }

        Theme theme = new Theme(
                themeRequestDto.getName(),
                themeRequestDto.getDesc(),
                themeRequestDto.getPrice()
        );
        return themeRepository.save(theme);
    }
}