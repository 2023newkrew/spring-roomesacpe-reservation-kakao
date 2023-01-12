package nextstep.web.theme.service;

import nextstep.domain.Theme;
import nextstep.web.theme.dto.*;
import nextstep.web.common.repository.RoomEscapeRepository;
import nextstep.web.theme.repository.ThemeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {

    private final RoomEscapeRepository<Theme> themeRepository;

    @Autowired
    public ThemeService(@Qualifier("themeDao") RoomEscapeRepository<Theme> themeRepository) {
        this.themeRepository = themeRepository;
    }

    public CreateThemeResponseDto createTheme(CreateThemeRequestDto requestDto) {
        Theme theme = Theme.from(requestDto);

        return CreateThemeResponseDto.from(themeRepository.save(theme));
    }

    public FindThemeResponseDto findTheme(Long id) {
        Theme theme = themeRepository.findById(id);

        return FindThemeResponseDto.of(theme);
    }

    public FindAllThemeResponseDto findAllTheme() {
        return new FindAllThemeResponseDto(themeRepository.findAll());
    }

    public void deleteTheme(Long id) {
        themeRepository.deleteById(id);
    }

    public void updateTheme(CreateThemeRequestDto requestDto, Long id) {
        ((ThemeDao) themeRepository).updateById(Theme.of(requestDto, id));
    }
}
