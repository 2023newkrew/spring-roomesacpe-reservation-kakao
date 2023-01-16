package roomescape.service.Theme;

import com.sun.jdi.request.DuplicateRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.repository.Theme.JdbcThemeRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static roomescape.utils.Messages.*;

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
            logger.error(CREATE_DUPLICATED.getMessage() + THEME_NAME.getMessage() + theme.getName()
                        + THEME_PRICE.getMessage() + theme.getPrice());
            throw new DuplicateRequestException("요청한 이름/가격의 테마가 이미 등록되어 있습니다.");
        }
        Long themeId = jdbcThemeRepository.createTheme(theme);
        logger.info(CREATE_SUCCESS.getMessage() + themeId);
        return new Theme(themeId, theme.getName(), theme.getDesc(), theme.getPrice());
    }

    @Override
    public Theme lookUpTheme(Long themeId) {
        try {
            Optional<Theme> theme = jdbcThemeRepository.findById(themeId);
            if (theme.isPresent()) {
                return theme.get();
            }
        } catch (Exception e){
            logger.error(NOT_FOUND_ERROR.getMessage() + themeId + ", " + e);
        }
        throw new NoSuchElementException("요청한 ID " + themeId + "를 조회할 수 없습니다");
    }

    @Override
    public void deleteTheme(Long deleteId) {
        Integer deleteResult = jdbcThemeRepository.deleteTheme(deleteId);
        if (deleteResult == 0){
            logger.error(DELETE_NOT_FOUND_ERROR.getMessage() + deleteId);
            throw new NoSuchElementException("요청한 ID: " + deleteId + "를 조회할 수 없습니다");
        }
        logger.info(DELETE_SUCCESS.getMessage() + deleteResult);
    }
}
