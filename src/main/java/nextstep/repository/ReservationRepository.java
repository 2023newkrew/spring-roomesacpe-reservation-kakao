package nextstep.repository;

import nextstep.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository {
    Reservation create(Reservation reservation);

    Reservation find(long id);

    List<Reservation> findAll();

    boolean delete(long id);

    boolean duplicate(LocalDate date, LocalTime time);
}
