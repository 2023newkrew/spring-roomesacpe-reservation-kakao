package nextstep.web.theme.service;

import nextstep.domain.Theme;
import nextstep.web.theme.dto.CreateThemeRequestDto;
import nextstep.web.theme.dto.CreateThemeResponseDto;
import nextstep.web.theme.dto.FindAllThemeResponseDto;
import nextstep.web.theme.dto.FindThemeResponseDto;
import nextstep.web.common.repository.RoomEscapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {

    private static final String BASE_URI = "/themes";

    private final RoomEscapeRepository<Theme> themeRepository;

    @Autowired
    public ThemeService(@Qualifier("themeDao") RoomEscapeRepository<Theme> themeRepository) {
        this.themeRepository = themeRepository;
    }

    public CreateThemeResponseDto createTheme(CreateThemeRequestDto requestDto) {
        Theme theme = Theme.of(
                requestDto.getName(),
                requestDto.getDesc(),
                requestDto.getPrice()
        );

        CreateThemeResponseDto responseDto = new CreateThemeResponseDto(
                BASE_URI + "/" + themeRepository.save(theme)
        );

        return responseDto;
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
}
