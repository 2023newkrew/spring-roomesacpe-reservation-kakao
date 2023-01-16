package kakao.repository;

import domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface ReservationRepository {
    long save(Reservation reservation);

    Reservation findById(Long id);

    List<Reservation> findByDateAndTime(LocalDate date, LocalTime time);

    List<Reservation> findByThemeId(Long themeId);

    int delete(Long id);
}
