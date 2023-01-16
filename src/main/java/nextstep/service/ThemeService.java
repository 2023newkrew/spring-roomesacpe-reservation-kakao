package nextstep.service;

import nextstep.domain.Theme;
import nextstep.dto.ThemeRequest;
import nextstep.dto.ThemeResponse;
import nextstep.exceptions.ErrorCode;
import nextstep.exceptions.exception.InvalidRequestException;
import nextstep.repository.ReservationDao;
import nextstep.repository.ThemeDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ThemeService {
    private ReservationDao reservationDao;
    private ThemeDao themeDao;

    public ThemeService(
            @Qualifier("reservationJdbcTemplateDao") ReservationDao reservationDao,
            @Qualifier("themeJdbcTemplateDao") ThemeDao themeDao
    ) {
        this.reservationDao = reservationDao;
        this.themeDao = themeDao;
    }

    public Long create(ThemeRequest themeRequest) {
        validateNameDuplication(themeRequest.getName());
        Theme theme = new Theme(
                themeRequest.getName(),
                themeRequest.getDesc(),
                themeRequest.getPrice()
        );
        return themeDao.save(theme);
    }

    private void validateNameDuplication(String name) {
        if (themeDao.findByName(name).isPresent()) {
            throw new InvalidRequestException(ErrorCode.THEME_NAME_DUPLICATED);
        }
    }

    public ThemeResponse retrieveOne(Long id) {
        return new ThemeResponse(getThemeById(id));
    }

    private Theme getThemeById(Long id) {
        Optional<Theme> themeFound = themeDao.findById(id);
        return themeFound.orElseThrow(() -> {
            throw new InvalidRequestException(ErrorCode.THEME_NOT_FOUND);
        });
    }

    public List<ThemeResponse> retrieveAll() {
        return themeDao.findAll()
                .stream()
                .map(ThemeResponse::new)
                .collect(Collectors.toList());
    }

    public void update(Long id, ThemeRequest themeRequest) {
        validateId(id);
        Theme themeFound = getThemeById(id);
        if (!themeFound.getName().equals(themeRequest.getName())) {
            validateNameDuplication(themeRequest.getName());
        }
        validateReservedOrNotByThemeId(id);
        Theme theme = new Theme(id, themeRequest.getName(), themeRequest.getDesc(), themeRequest.getPrice());
        themeDao.update(theme);
    }

    private void validateReservedOrNotByThemeId(Long id) {
        if (reservationDao.countByThemeId(id) > 0) {
            throw new InvalidRequestException(ErrorCode.RESERVATION_EXIST);
        }
    }

    private void validateId(Long id) {
        if (id <= 0) {
            throw new InvalidRequestException(ErrorCode.INPUT_PARAMETER_INVALID);
        }
    }

    public void delete(Long id) {
        validateId(id);
        getThemeById(id);
        validateReservedOrNotByThemeId(id);
        themeDao.delete(id);
    }
}
