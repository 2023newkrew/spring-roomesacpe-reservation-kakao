package kakao.repository;

import kakao.controller.request.ReservationRequest;
import kakao.controller.response.ReservationResponse;
import kakao.model.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    public static final String TABLE_NAME = "reservation";

    Long create(ReservationRequest reservationRequest, Theme theme);
    Optional<ReservationResponse> findById(Long id);
    List<ReservationResponse> findByDateAndTime(LocalDate date, LocalTime time);
    void deleteById(Long id);
}
