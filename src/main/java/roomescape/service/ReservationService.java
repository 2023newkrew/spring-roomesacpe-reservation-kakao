package roomescape.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.Themes;
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
        checkTimeDuplication(date, time);
        Reservation reservation = reservationRequest.toEntity(Themes.WANNA_GO_HOME);
        return reservationWebRepository.insertReservation(reservation);
    }

    private void checkTimeDuplication(LocalDate date, LocalTime time) {
        reservationWebRepository.getReservationByDateAndTime(date, time)
                .ifPresent(reservation -> {
                    throw new RoomEscapeException(ErrorCode.DUPLICATED_RESERVATION);
                });
    }

    public ReservationResponse getReservation(Long reservationId) {
        Reservation reservation = reservationWebRepository.getReservation(reservationId)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.RESERVATION_NOT_FOUND));
        return ReservationResponse.fromEntity(reservation);
    }

    public void deleteReservation(Long reservationId) {
        reservationWebRepository.deleteReservation(reservationId);
    }
}
