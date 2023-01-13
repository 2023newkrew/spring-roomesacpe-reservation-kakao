package nextstep.web.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;

public interface ReservationRepository {
    Long insertWithKeyHolder(Reservation reservation);

    Reservation findById(Long id);

    List<Reservation> findByDateAndTime(LocalDate localDate, LocalTime localTime);

    List<Reservation> findByTheme(Theme theme);

    Integer delete(Long id);
}
