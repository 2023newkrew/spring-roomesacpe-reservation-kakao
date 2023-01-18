package nextstep.reservation.repository.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.reservation.entity.Reservation;
import java.util.Optional;


public interface ReservationRepository {

    Long add(Reservation reservation);

    Optional<Reservation> findById(Long id);

    List<Reservation> findAll();

    boolean delete(Long id);

    Optional<Reservation> getReservationByDateAndTime(LocalDate date, LocalTime time);

    Optional<Reservation> getReservationByName(String name);

}
