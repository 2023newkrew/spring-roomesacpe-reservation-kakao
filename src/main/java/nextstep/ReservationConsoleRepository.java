package nextstep;

import domain.Reservation;
import kakao.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationConsoleRepository implements ReservationRepository {
    private final ReservationDAO reservationDAO = new ReservationDAO();

    @Override
    public long save(Reservation reservation) {
        return reservationDAO.addReservation(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationDAO.findById(id);
    }

    @Override
    public List<Reservation> findByDateAndTime(LocalDate date, LocalTime time) {
        return reservationDAO.findByDateAndTIme(date, time);
    }

    @Override
    public List<Reservation> findByThemeId(Long themeId) {
        return reservationDAO.findByThemeId(themeId);
    }

    @Override
    public int delete(Long id) {
        return reservationDAO.delete(id);
    }
}
