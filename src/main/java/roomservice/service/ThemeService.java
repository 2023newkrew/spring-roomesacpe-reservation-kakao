package roomservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomservice.domain.dto.ThemeCreateDto;
import roomservice.domain.dto.ThemeFindResultDto;
import roomservice.domain.entity.Theme;
import roomservice.repository.ThemeDao;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {
    private ThemeDao themeDao;
    @Autowired
    public ThemeService(ThemeDao themeDao){
        this.themeDao = themeDao;
    }

    public Long createTheme(@Valid ThemeCreateDto themeDto){
        Theme theme = new Theme(
                null,
                themeDto.getName(),
                themeDto.getDesc(),
                themeDto.getPrice());
        return themeDao.createTheme(theme);
    }

    public Theme findThemeById(long id){
        return themeDao.selectThemeById(id);
    }

    public void deleteThemeById(long id){
        themeDao.deleteThemeById(id);
    }

    public List<ThemeFindResultDto> findAllTheme() {
        List<Theme> themes = themeDao.selectAllTheme();
        List<ThemeFindResultDto> result = Arrays.stream(themes.toArray(new Theme[0]))
                .map(theme -> {
                    return new ThemeFindResultDto(theme.getId(), theme.getName(),
                            theme.getDesc(), theme.getPrice());
                }).collect(Collectors.toList());
        return result;
    }
}
