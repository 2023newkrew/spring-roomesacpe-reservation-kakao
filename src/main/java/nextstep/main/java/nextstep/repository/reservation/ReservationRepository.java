package nextstep.main.java.nextstep.repository.reservation;

import nextstep.main.java.nextstep.domain.reservation.Reservation;
import nextstep.main.java.nextstep.domain.reservation.ReservationCreateRequestDto;
import nextstep.main.java.nextstep.domain.theme.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface ReservationRepository {
    Long save(ReservationCreateRequestDto request);

    Optional<Reservation> findOne(Long id);

    void deleteOne(Long id);

    Boolean existsByDateAndTime(LocalDate date, LocalTime time);
}
