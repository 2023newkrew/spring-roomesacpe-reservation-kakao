package roomescape.service;


import org.springframework.stereotype.Service;
import roomescape.RoomEscapeApplication;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.repository.ReservationWebRepository;
import roomescape.domain.TimeTable;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {
    private final ReservationWebRepository reservationWebRepository;

    public ReservationService(ReservationWebRepository reservationWebRepository) {
        this.reservationWebRepository = reservationWebRepository;
    }

    public void createReservation(ReservationRequest reservationRequest) {
        LocalDate date = reservationRequest.getDate();
        LocalTime time = reservationRequest.getTime();
        validateTime(time);
        checkTimeDuplication(date, time);
        reservationWebRepository.insertReservation(
                reservationRequest.toEntity(RoomEscapeApplication.theme)
        );
    }

    private void validateTime(LocalTime time) {
        if (!TimeTable.isExist(time)) {
            throw new RoomEscapeException(ErrorCode.TIME_TABLE_NOT_AVAILABLE);
        }
    }

    private void checkTimeDuplication(LocalDate date, LocalTime time) {
        reservationWebRepository.getReservationByDateAndTime(date, time)
                .ifPresent(reservation -> {
                    throw new RoomEscapeException(ErrorCode.DUPLICATED_RESERVATION);
                });
    }

    public ReservationResponse getReservation(Long reservationId) {
        return ReservationResponse.fromEntity(
                reservationWebRepository.getReservation(reservationId)
                        .orElseThrow(() -> new RoomEscapeException(ErrorCode.RESERVATION_NOT_FOUND))
        );
    }

    public void deleteReservation(Long reservationId) {
        reservationWebRepository.deleteReservation(reservationId);
    }
}
