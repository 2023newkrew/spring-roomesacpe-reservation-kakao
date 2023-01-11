package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.ReservationRequest;
import nextstep.dto.ReservationResponse;
import nextstep.exceptions.ErrorCode;
import nextstep.exceptions.exception.InvalidRequestException;
import nextstep.repository.ReservationDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(@Qualifier("reservationJdbcTemplateDao") ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Long reserve(ReservationRequest reservationRequest) {
        LocalDate date = reservationRequest.getDate();
        LocalTime time = reservationRequest.getTime();
        if (reservationDao.countByDateAndTime(date, time) > 0) {
            throw new InvalidRequestException(ErrorCode.RESERVATION_DUPLICATED);
        }

        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
        Reservation newReservation = new Reservation(date, time, reservationRequest.getName(), theme);

        return reservationDao.save(newReservation);
    }

    public ReservationResponse retrieve(Long id) {
        Optional<Reservation> reservation = reservationDao.findById(id);
        if (reservation.isEmpty()) {
            throw new InvalidRequestException(ErrorCode.RESERVATION_NOT_FOUND);
        }
        return new ReservationResponse(reservation.get());
    }

    public void delete(Long id) {
        if (reservationDao.findById(id).isEmpty()) {
            throw new InvalidRequestException(ErrorCode.RESERVATION_NOT_FOUND);
        }
        reservationDao.delete(id);
    }
}
