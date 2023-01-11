package nextstep.service;

import nextstep.domain.theme.Theme;
import nextstep.domain.theme.repository.ThemeRepository;
import nextstep.dto.request.CreateThemeRequest;
import nextstep.error.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static nextstep.error.ErrorType.DUPLICATE_THEME;
import static nextstep.error.ErrorType.THEME_NOT_FOUND;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    @Transactional
    public Long createTheme(CreateThemeRequest createThemeRequest) {
        if (themeRepository.findByName(createThemeRequest.getName()).isPresent()) {
            throw new ApplicationException(DUPLICATE_THEME);
        }

        Theme savedTheme = themeRepository.save(new Theme(createThemeRequest.getName(), createThemeRequest.getDesc(), createThemeRequest.getPrice()));
        return savedTheme.getId();
    }

    @Transactional(readOnly = true)
    public Theme findThemeByName(String name) {
        return themeRepository.findByName(name)
                .orElseThrow(() -> new ApplicationException(THEME_NOT_FOUND));
    }

}
