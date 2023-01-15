package roomescape.service.Theme;

import com.sun.jdi.request.DuplicateRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.repository.Theme.JdbcThemeRepository;

import java.util.Optional;

@Service
@Qualifier("WebTheme")
public class WebThemeService implements ThemeService {
    JdbcThemeRepository jdbcThemeRepository;
    private static final Logger logger =
            LoggerFactory.getLogger(WebThemeService.class);

    public WebThemeService(JdbcThemeRepository jdbcThemeRepository) {
        this.jdbcThemeRepository = jdbcThemeRepository;
    }

    @Override
    public Theme createTheme(Theme theme) {
        if (jdbcThemeRepository.findIdByDateAndTime(theme) == 1) {
            logger.error("Theme createDuplicatedError," +
                    "name:" + theme.getName() +", price:" + theme.getPrice());
            throw new DuplicateRequestException("요청한 이름/가격의 테마가 이미 등록되어 있습니다.");
        }
        Long themeId = jdbcThemeRepository.createTheme(theme);
        if (themeId > 0){
            logger.info("Theme createTheme, Id: " + themeId);
            return new Theme(themeId, theme.getName(), theme.getDesc(), theme.getPrice());
        }
        logger.error("Theme create QueryError");
        return new Theme();
    }

    @Override
    public Theme lookUpTheme(Long themeId) {
        Optional<Theme> theme = jdbcThemeRepository.findById(themeId);
        if (theme.isPresent()) {
            return theme.get();
        }
        logger.error("Theme findError, NotFound Id: " + themeId);
        return new Theme();
    }

    @Override
    public void deleteTheme(Long deleteId) {
        Integer deleteResult = jdbcThemeRepository.deleteTheme(deleteId);
        if (deleteResult != 1){
            logger.error("Theme DeleteError, NotFound Id: " + deleteId);
            throw new IllegalArgumentException("존재하지 않는 테마 ID 입니다.");
        }
        logger.info("Theme DeleteSuccess Id: " + deleteResult);
    }
}
