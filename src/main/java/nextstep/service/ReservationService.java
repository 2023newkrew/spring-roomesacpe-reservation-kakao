package nextstep.service;

import nextstep.dao.ReservationDAO;
import nextstep.domain.Reservation;
import nextstep.domain.ReservationSaveForm;
import nextstep.exceptions.DataConflictException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationJdbcTemplateDAO) {
        this.reservationDAO = reservationJdbcTemplateDAO;
    }

    public Long saveReservation(ReservationSaveForm reservationSaveForm) {
        List<Reservation> reservationsByDateAndTime = reservationDAO.findByDateAndTime(reservationSaveForm.getDate(), reservationSaveForm.getTime());
        if (reservationsByDateAndTime.size() > 0) {
            throw new DataConflictException("동일한 시간대에 예약이 이미 존재합니다.");
        }

        return reservationDAO.save(reservationSaveForm);
    }

    public Reservation findReservation(Long id) {
        return reservationDAO.findById(id);
    }

    public void deleteReservation(Long id) {
        reservationDAO.deleteById(id);
    }
}
