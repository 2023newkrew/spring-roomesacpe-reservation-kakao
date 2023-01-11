package kakao.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import kakao.domain.Reservation;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Reservation findById(Long id);

    List<Reservation> findByThemeIdAndDateAndTime(Long themeId, LocalDate date, LocalTime time);

    int delete(Long id);
}
