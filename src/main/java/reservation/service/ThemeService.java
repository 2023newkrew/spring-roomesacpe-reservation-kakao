package reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.model.domain.Theme;
import reservation.model.dto.RequestTheme;
import reservation.respository.ThemeJdbcTemplateRepository;
import reservation.util.exception.restAPI.DuplicateException;
import java.util.List;

import static reservation.util.exception.ErrorMessages.THEME_DUPLICATED;

@Service
public class ThemeService {

    private final ThemeJdbcTemplateRepository themeJdbcTemplateRepository;

    @Autowired
    public ThemeService(ThemeJdbcTemplateRepository themeJdbcTemplateRepository) {
        this.themeJdbcTemplateRepository = themeJdbcTemplateRepository;
    }

    public Long createTheme(RequestTheme requestTheme) {
        // 같은 이름의 테마가 존재하는지 validate
        if(themeJdbcTemplateRepository.checkDuplicateName(requestTheme.getName())){
            throw new DuplicateException(THEME_DUPLICATED);
        }
        return themeJdbcTemplateRepository.save(changeToTheme(requestTheme));
    }

    public List<Theme> getAllTheme() {
        return themeJdbcTemplateRepository.findAll();
    }

    private Theme changeToTheme(RequestTheme requestTheme) {
        return new Theme(0L, requestTheme.getName(), requestTheme.getDesc(), requestTheme.getPrice());
    }
}
