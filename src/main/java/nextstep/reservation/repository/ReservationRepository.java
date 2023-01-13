package nextstep.reservation.repository;

import java.util.List;
import nextstep.reservation.entity.Reservation;
import java.util.Optional;


public interface ReservationRepository {

    Long add(Reservation reservation);

    Optional<Reservation> findById(Long id);

    List<Reservation> findAll();

    boolean delete(Long id);

}
