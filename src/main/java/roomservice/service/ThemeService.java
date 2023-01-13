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

/**
 * ThemeService processes interior logic of theme-managing system.
 * This class can request DAO to get information from database.
 */
@Service
public class ThemeService {
    private ThemeDao themeDao;

    @Autowired
    public ThemeService(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    /**
     * Request DAO to insert theme, with some validations.
     *
     * @param themeDto information of theme to be added.
     * @return id if successfully created.
     */
    public Long createTheme(@Valid ThemeCreateDto themeDto) {
        Theme theme = new Theme(
                null,
                themeDto.getName(),
                themeDto.getDesc(),
                themeDto.getPrice());
        return themeDao.createTheme(theme);
    }

    /**
     * Request repository to insert theme, with some validations.
     *
     * @param id which you want to find.
     * @return information of found theme. if not found, return null.
     */
    public ThemeFindResultDto findThemeById(long id) {
        Theme theme = themeDao.selectThemeById(id);
        if (theme == null) {
            return null;
        }
        ThemeFindResultDto resultDto = new ThemeFindResultDto(
                theme.getId(),
                theme.getName(),
                theme.getDesc(),
                theme.getPrice()
        );
        return resultDto;
    }

    /**
     * Request repository to find all themes in DB.
     *
     * @return all lists of information of theme.
     */
    public List<ThemeFindResultDto> findAllTheme() {
        List<Theme> themes = themeDao.selectAllTheme();
        List<ThemeFindResultDto> result = Arrays.stream(themes.toArray(new Theme[0]))
                .map(theme -> {
                    return new ThemeFindResultDto(theme.getId(), theme.getName(),
                            theme.getDesc(), theme.getPrice());
                }).collect(Collectors.toList());
        return result;
    }

    /**
     * Request repository to delete specific theme.
     */
    public void deleteThemeById(long id) {
        themeDao.deleteThemeById(id);
    }
}
