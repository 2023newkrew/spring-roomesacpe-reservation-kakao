package kakao.repository.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import kakao.domain.Reservation;
import kakao.domain.Theme;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAllByTheme(Theme theme);

    Reservation findById(Long id);

    List<Reservation> findByThemeIdAndDateAndTime(Long themeId, LocalDate date, LocalTime time);

    int delete(Long id);
}
