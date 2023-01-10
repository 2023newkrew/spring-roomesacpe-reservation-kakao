package roomescape.service;


import org.springframework.stereotype.Service;
import roomescape.RoomEscapeApplication;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.repository.ReservationWebRepository;
import roomescape.repository.Reservations;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {
    private final ReservationWebRepository reservationWebRepository;

    public ReservationService(ReservationWebRepository reservationWebRepository) {
        this.reservationWebRepository = reservationWebRepository;
    }

    public void createReservation(ReservationRequest reservationRequest) {
//        LocalDate date = reservationRequest.getDate();
//        LocalTime time = reservationRequest.getTime();
//        if (Reservations.isNotAvailable(date, time)) {
//            throw new RoomEscapeException(ErrorCode.DUPLICATED_RESERVATION);
//        }
//        Reservations.putReservation(
//                reservationRequest.toEntity(RoomEscapeApplication.theme)
//        );
        reservationWebRepository.insertReservation(
                reservationRequest.toEntity(RoomEscapeApplication.theme)
        );
    }

    public ReservationResponse getReservation(Long reservationId) {
//        return ReservationResponse.fromEntity(
//                Reservations.getReservation(reservationId)
//        );
        return ReservationResponse.fromEntity(
                reservationWebRepository.getReservation(reservationId)
                        .orElseThrow(() -> new RoomEscapeException(ErrorCode.RESERVATION_NOT_FOUND))
        );
    }

    public void deleteReservation(Long reservationId) {
//        Reservations.deleteReservation(reservationId);
        reservationWebRepository.deleteReservation(reservationId);
    }
}
