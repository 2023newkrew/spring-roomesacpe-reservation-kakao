package nextstep.roomescape.reservation;

import nextstep.roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository {
    Reservation create(Reservation reservation);
    Reservation findById(long id);
    Boolean findByDateTime(LocalDate date, LocalTime time);
    Boolean delete(long id);
    void clear();
}
