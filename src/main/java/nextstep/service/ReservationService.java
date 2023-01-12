package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.ReservationRequest;
import nextstep.dto.ReservationResponse;
import nextstep.exceptions.ErrorCode;
import nextstep.exceptions.exception.InvalidRequestException;
import nextstep.repository.ReservationDao;
import nextstep.repository.ThemeDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ThemeDao themeDao;

    public ReservationService(
            @Qualifier("reservationJdbcTemplateDao") ReservationDao reservationDao,
            @Qualifier("themeJdbcTemplateDao") ThemeDao themeDao
    ) {
        this.reservationDao = reservationDao;
        this.themeDao = themeDao;
    }

    public Long reserve(ReservationRequest reservationRequest) {
        LocalDate date = reservationRequest.getDate();
        LocalTime time = reservationRequest.getTime();
        Long themeId = reservationRequest.getThemeId();

        if (reservationDao.countByDateAndTimeAndThemeId(date, time, themeId) > 0) {
            throw new InvalidRequestException(ErrorCode.RESERVATION_DUPLICATED);
        }

        validateId(themeId);
        Optional<Theme> themeFound = themeDao.findById(themeId);
        if (themeFound.isEmpty()) {
            throw new InvalidRequestException(ErrorCode.THEME_NOT_FOUND);
        }

        Reservation newReservation = new Reservation(date, time, reservationRequest.getName(), themeFound.get());
        return reservationDao.save(newReservation);
    }

    public ReservationResponse retrieve(Long id) {
        validateId(id);
        Optional<Reservation> reservationFound = reservationDao.findById(id);
        if (reservationFound.isEmpty()) {
            throw new InvalidRequestException(ErrorCode.RESERVATION_NOT_FOUND);
        }
        return new ReservationResponse(reservationFound.get());
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException(ErrorCode.INPUT_PARAMETER_INVALID);
        }
    }

    public void delete(Long id) {
        validateId(id);
        if (reservationDao.findById(id).isEmpty()) {
            throw new InvalidRequestException(ErrorCode.RESERVATION_NOT_FOUND);
        }
        reservationDao.delete(id);
    }
}
