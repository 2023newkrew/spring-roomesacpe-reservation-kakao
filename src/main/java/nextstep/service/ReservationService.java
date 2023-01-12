package nextstep.service;

import nextstep.dao.ReservationDAO;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.exceptions.DataConflictException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private ReservationDAO reservationDAO;
    private final Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    public ReservationService(ReservationDAO reservationJdbcTemplateDAO) {
        this.reservationDAO = reservationJdbcTemplateDAO;
    }

    public Long saveReservation(Reservation reservation) {
        List<Reservation> reservationsByDateAndTime = reservationDAO.findByDateAndTime(reservation.getDate(), reservation.getTime());
        if (reservationsByDateAndTime.size() > 0) {
            throw new DataConflictException("동일한 시간대에 예약이 이미 존재합니다.");
        }

        Reservation newReservation = new Reservation(reservation.getDate(), reservation.getTime(), reservation.getName(), theme);
        return reservationDAO.save(newReservation);
    }
}
