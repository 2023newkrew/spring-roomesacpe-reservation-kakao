package nextstep.main.java.nextstep.mvc.repository.reservation;

import nextstep.main.java.nextstep.mvc.domain.reservation.Reservation;
import nextstep.main.java.nextstep.mvc.domain.reservation.request.ReservationCreateRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface ReservationRepository {
    Long save(ReservationCreateRequest request);

    Optional<Reservation> findById(Long id);

    void deleteById(Long id);

    Boolean existsByDateAndTime(LocalDate date, LocalTime time);

    Boolean existsById(Long id);
}
