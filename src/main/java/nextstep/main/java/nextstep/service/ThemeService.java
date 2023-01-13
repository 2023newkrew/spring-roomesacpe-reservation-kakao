package nextstep.main.java.nextstep.service;

import nextstep.main.java.nextstep.domain.Theme;
import nextstep.main.java.nextstep.domain.ThemeCreateRequestDto;
import nextstep.main.java.nextstep.exception.exception.DuplicateThemeException;
import nextstep.main.java.nextstep.exception.exception.NoSuchThemeException;
import nextstep.main.java.nextstep.message.ExceptionMessage;
import nextstep.main.java.nextstep.repository.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static nextstep.main.java.nextstep.message.ExceptionMessage.*;

@Service
public class ThemeService {

    private ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme createTheme(ThemeCreateRequestDto themeCreateRequestDto) {
        if (themeRepository.findByName(themeCreateRequestDto.getName())
                .isPresent()) {
            throw new DuplicateThemeException(DUPLICATE_THEME_MESSAGE);
        }
        return themeRepository.save(new Theme(themeCreateRequestDto.getName(), themeCreateRequestDto.getDesc(), themeCreateRequestDto.getPrice()));
    }

    public List<Theme> findAllTheme() {
        return themeRepository.findAll();
    }

    public void deleteTheme(Long id) {
        if (themeRepository.findById(id)
                .isPresent()) {
            throw new NoSuchThemeException(NO_SUCH_THEME_MESSAGE);
        }
        themeRepository.deleteById(id);
    }
}
