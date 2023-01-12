package roomescape.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.Themes;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Long createReservation(ReservationRequest reservationRequest) {
        LocalDate date = reservationRequest.getDate();
        LocalTime time = reservationRequest.getTime();
        checkTimeDuplication(date, time);
        Reservation reservation = reservationRequest.toEntity(Themes.WANNA_GO_HOME);
        return reservationRepository.insertReservation(reservation);
    }

    private void checkTimeDuplication(LocalDate date, LocalTime time) {
        reservationRepository.getReservationByDateAndTime(date, time)
                .ifPresent(reservation -> {
                    throw new RoomEscapeException(ErrorCode.DUPLICATED_RESERVATION);
                });
    }

    public ReservationResponse getReservation(Long reservationId) {
        Reservation reservation = reservationRepository.getReservation(reservationId)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.RESERVATION_NOT_FOUND));
        return ReservationResponse.fromEntity(reservation);
    }

    @Transactional
    public void deleteReservation(Long reservationId) {
        reservationRepository.getReservation(reservationId)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.RESERVATION_NOT_FOUND));
        reservationRepository.deleteReservation(reservationId);
    }
}
