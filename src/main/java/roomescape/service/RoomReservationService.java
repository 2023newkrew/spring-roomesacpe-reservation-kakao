package roomescape.service;


import org.springframework.stereotype.Service;
import roomescape.RoomEscapeApplication;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.repository.Reservations;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class RoomReservationService {

    public void createReservation(ReservationRequest reservationRequest) {
        LocalDate date = reservationRequest.getDate();
        LocalTime time = reservationRequest.getTime();
        if (Reservations.isNotAvailable(date, time)) {
            throw new RoomEscapeException(ErrorCode.DUPLICATED_RESERVATION);
        }
        Reservations.putReservation(
                reservationRequest.toEntity(RoomEscapeApplication.theme)
        );
    }

    public ReservationResponse getReservation(Long reservationId) {
        return ReservationResponse.fromEntity(
                Reservations.getReservation(reservationId)
        );
    }

    public void deleteReservation(Long reservationId) {
        Reservations.deleteReservation(reservationId);
    }
}
