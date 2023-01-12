package nextstep.main.java.nextstep.service;

import nextstep.main.java.nextstep.domain.Theme;
import nextstep.main.java.nextstep.domain.ThemeCreateRequestDto;
import nextstep.main.java.nextstep.exception.exception.DuplicateThemeException;
import nextstep.main.java.nextstep.repository.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {

    private ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme createTheme(ThemeCreateRequestDto themeCreateRequestDto) {
        if (themeRepository.findByName(themeCreateRequestDto.getName())
                .isPresent()) {
            throw new DuplicateThemeException();
        }
        return themeRepository.save(new Theme(themeCreateRequestDto.getName(), themeCreateRequestDto.getDesc(), themeCreateRequestDto.getPrice()));
    }
}
