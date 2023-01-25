package roomescape.service.Theme;

import com.sun.jdi.request.DuplicateRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.repository.Reservation.JdbcReservationRepository;
import roomescape.repository.Theme.JdbcThemeRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static roomescape.utils.Messages.*;

@Service
@Qualifier("WebTheme")
public class WebThemeService implements ThemeService {
    private final JdbcThemeRepository jdbcThemeRepository;
    private final JdbcReservationRepository jdbcReservationRepository;
    private static final Logger logger =
            LoggerFactory.getLogger(WebThemeService.class);

    public WebThemeService(JdbcThemeRepository jdbcThemeRepository, JdbcReservationRepository jdbcReservationRepository) {
        this.jdbcThemeRepository = jdbcThemeRepository;
        this.jdbcReservationRepository = jdbcReservationRepository;
    }

    @Override
    public Theme createTheme(Theme theme) {
        if (jdbcThemeRepository.findCountByNameAndPrice(theme) == 1) {
            logger.error(CREATE_DUPLICATED.getMessage() + THEME_NAME.getMessage() + theme.getName()
                        + THEME_PRICE.getMessage() + theme.getPrice());
            throw new DuplicateRequestException(THEME_CREATE_ERROR.getMessage());
        }
        Long themeId = jdbcThemeRepository.createTheme(theme);
        logger.info(CREATE_SUCCESS.getMessage() + themeId);
        return new Theme(themeId, theme.getName(), theme.getDesc(), theme.getPrice());
    }

    @Override
    public Theme lookUpTheme(Long themeId) {
        try {
            Optional<Theme> theme = jdbcThemeRepository.findThemeById(themeId);
            if (theme.isPresent()) {
                return theme.get();
            }
        } catch (Exception e){
            logger.error(NOT_FOUND_ERROR.getMessage() + themeId + ", " + e);
        }
        throw new NoSuchElementException(ID_NOT_FOUND_ERROR.getMessage() + themeId);
    }

    @Override
    public void deleteTheme(Long deleteId) {
        Boolean isReserved = jdbcReservationRepository.isReservation(deleteId);
        if (isReserved) {
            logger.error(DELETE_THEME_ERROR.getMessage() + deleteId);
            throw new IllegalArgumentException(THEME_EXISTS_RESERVATION.getMessage() + deleteId);
        }
        Integer deleteResult = jdbcThemeRepository.deleteTheme(deleteId);
        if (deleteResult == 0){
            logger.error(DELETE_NOT_FOUND_ERROR.getMessage() + deleteId);
            throw new NoSuchElementException(ID_NOT_FOUND_ERROR.getMessage() + deleteId);
        }
        logger.info(DELETE_SUCCESS.getMessage() + deleteId);
    }
}