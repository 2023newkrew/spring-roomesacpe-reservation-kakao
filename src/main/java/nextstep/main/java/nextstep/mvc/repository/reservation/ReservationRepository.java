package nextstep.main.java.nextstep.mvc.repository.reservation;

import nextstep.main.java.nextstep.mvc.domain.reservation.Reservation;
import nextstep.main.java.nextstep.mvc.domain.reservation.request.ReservationCreateRequest;
import nextstep.main.java.nextstep.mvc.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository extends CrudRepository<ReservationCreateRequest, Reservation> {

    Boolean existsByDateAndTime(LocalDate date, LocalTime time);

    Boolean existsByThemeId(Long themeId);
}
