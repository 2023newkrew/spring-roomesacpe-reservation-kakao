package kakao.repository;

import kakao.controller.request.ReservationRequest;
import kakao.controller.response.ReservationResponse;
import kakao.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    public static final String TABLE_NAME = "reservation";

    Long create(ReservationRequest reservationRequest);
    Optional<Reservation> findById(Long id);
    Optional<Reservation> findByDateAndTimeAndThemeId(LocalDate date, LocalTime time, Long themeId);
    List<Reservation> findByThemeId(Long themeId);
    void deleteById(Long id);
}
