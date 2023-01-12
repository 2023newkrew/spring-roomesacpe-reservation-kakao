package nextstep.service;

import nextstep.dao.ReservationDAO;
import nextstep.domain.Reservation;
import nextstep.domain.ReservationSaveForm;
import nextstep.exceptions.DataConflictException;
import nextstep.exceptions.DataNotExistException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationJdbcTemplateDAO) {
        this.reservationDAO = reservationJdbcTemplateDAO;
    }

    public Long saveReservation(ReservationSaveForm reservationSaveForm) {
        List<Reservation> reservationsByDateAndTime = reservationDAO.findByDateAndTimeAndThemeId(
                reservationSaveForm.getDate(),
                reservationSaveForm.getTime(),
                reservationSaveForm.getThemeId()
        );
        if (reservationsByDateAndTime.size() > 0) {
            throw new DataConflictException("동일한 시간대, 동일한 테마에 예약이 이미 존재합니다.");
        }

        return reservationDAO.save(reservationSaveForm);
    }

    public Reservation findReservation(Long id) {
        return reservationDAO.findById(id)
                .orElseThrow(() -> new DataNotExistException("예약이 존재하지 않습니다."));
    }

    public void deleteReservation(Long id) {
        if (reservationDAO.deleteById(id) == 0) {
            throw new DataNotExistException("예약이 존재하지 않습니다.");
        }
    }
}
