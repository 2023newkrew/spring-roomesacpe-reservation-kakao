package nextstep.roomescape.reservation.repository;

import nextstep.roomescape.reservation.repository.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository {
    Long create(Reservation reservation);
    Reservation findById(long id);
    Boolean findByDateTime(LocalDate date, LocalTime time);
    void delete(long id);

    List<Reservation> findByThemeId(Long themeId);
}
