package roomescape.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.Themes;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.repository.ReservationWebRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {
    private final ReservationWebRepository reservationWebRepository;

    public ReservationService(ReservationWebRepository reservationWebRepository) {
        this.reservationWebRepository = reservationWebRepository;
    }

    @Transactional
    public Long createReservation(ReservationRequest reservationRequest) {
        LocalDate date = reservationRequest.getDate();
        LocalTime time = reservationRequest.getTime();
        Reservation reservation = reservationRequest.toEntity(Themes.WANNA_GO_HOME);

        checkTimeDuplication(date, time);

        return reservationWebRepository.save(reservation);
    }

    private void checkTimeDuplication(LocalDate date, LocalTime time) {
        reservationWebRepository.findReservationByDateAndTime(date, time)
                .ifPresent(reservation -> {
                    throw new RoomEscapeException(ErrorCode.DUPLICATED_RESERVATION);
                });
    }

    @Transactional
    public ReservationResponse getReservation(Long reservationId) {
        return ReservationResponse.fromEntity(
                reservationWebRepository.findOne(reservationId)
                        .orElseThrow(() -> new RoomEscapeException(ErrorCode.RESERVATION_NOT_FOUND))
        );
    }

    @Transactional
    public void deleteReservation(Long reservationId) {
        reservationWebRepository.delete(reservationId);
    }
}
