package roomservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomservice.domain.dto.ThemeDto;
import roomservice.domain.entity.Theme;
import roomservice.repository.ThemeDao;

import javax.validation.Valid;

@Service
public class ThemeService {
    private ThemeDao themeDao;
    @Autowired
    public ThemeService(ThemeDao themeDao){
        this.themeDao = themeDao;
    }

    public Long createTheme(@Valid ThemeDto themeDto){
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
}
