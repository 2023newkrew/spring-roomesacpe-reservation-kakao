package nextstep.web.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.web.VO.ReservationRequestVO;
import nextstep.web.exceptions.ErrorCode;
import nextstep.web.exceptions.ReservationException;
import nextstep.web.repository.ReservationDAOImpl;
import nextstep.web.repository.ThemeDAOImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationDAOImpl reservationDAOImpl;
    private final ThemeDAOImpl themeDAOImpl;

    public ReservationService(ReservationDAOImpl reservationDAOImpl, ThemeDAOImpl themeDAOImpl) {
        this.reservationDAOImpl = reservationDAOImpl;
        this.themeDAOImpl = themeDAOImpl;
    }

    private Theme findThemeByName(String themeName) {
        try {
            return themeDAOImpl.findByName(themeName);
        } catch(EmptyResultDataAccessException e) {
            throw new ReservationException(ErrorCode.THEME_NOT_FOUND);
        }
    }

    private void checkReservationException(LocalDate localDate, LocalTime localTime) {
        List<Reservation> reservationsByDateAndTime = reservationDAOImpl.findByDateAndTime(
                localDate, localTime);
        if (reservationsByDateAndTime.size() > 0) {
            throw new ReservationException(ErrorCode.ALREADY_RESERVATION_EXISTS);
        }
    }

    public Long createReservation(ReservationRequestVO reservationRequestVO) {
        checkReservationException(reservationRequestVO.getDate(), reservationRequestVO.getTime());
        Theme theme = findThemeByName(reservationRequestVO.getThemeName());
        Reservation reservation = new Reservation(
                reservationRequestVO.getDate(),
                reservationRequestVO.getTime(),
                reservationRequestVO.getName(),
                theme);
        return reservationDAOImpl.insertWithKeyHolder(reservation);
    }

    public Reservation lookupReservation(Long id) {
        try {
            return reservationDAOImpl.findById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new ReservationException(ErrorCode.RESERVATION_NOT_FOUND);
        }
    }

    public Reservation deleteReservation(Long id) {
        try {
            Reservation reservation = reservationDAOImpl.findById(id);
            reservationDAOImpl.delete(id);
            return reservation;
        } catch(EmptyResultDataAccessException e) {
            throw new ReservationException(ErrorCode.RESERVATION_NOT_FOUND);
        }
    }
}
