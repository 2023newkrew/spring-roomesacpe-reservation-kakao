package nextstep.service;

import nextstep.domain.Theme;
import nextstep.repository.theme.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    @Autowired
    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme findById(Long id){
        return themeRepository.findByThemeId(id);
    }

    public List<Theme> findAll(){
        return themeRepository.findAll();
    }

    public void deleteById(Long id) throws SQLException {
        themeRepository.deleteById(id);
    }

    public Long createTheme(Theme theme){
        return themeRepository.save(theme);
    }

    public Theme findByTheme(Theme theme){
        return themeRepository.findByTheme(theme);
    }
}
