package nextstep.roomescape.reservation.repository;

import nextstep.roomescape.reservation.repository.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository {
    Long create(Reservation reservation);
    Reservation findById(long id);
    Boolean findByDateTime(LocalDate date, LocalTime time);
    void delete(long id);
}
