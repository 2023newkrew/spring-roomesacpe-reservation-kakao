package roomescape.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.Themes;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.mapper.ReservationMapper;
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
        LocalDate requestedDate = reservationRequest.getDate();
        LocalTime requestedTime = reservationRequest.getTime();
        reservationRequest.setTheme(Themes.WANNA_GO_HOME);

        checkTimeDuplication(requestedDate, requestedTime);

        return reservationWebRepository.save(
                ReservationMapper.INSTANCE.reservationRequestToReservation(reservationRequest)
        );
    }

    private void checkTimeDuplication(LocalDate date, LocalTime time) {
        reservationWebRepository.findReservationByDateAndTime(date, time)
                .ifPresent(reservation -> {
                    throw new RoomEscapeException(ErrorCode.DUPLICATED_RESERVATION);
                });
    }

    @Transactional
    public ReservationResponse getReservation(Long reservationId) {
        Reservation reservation = reservationWebRepository.findOne(reservationId)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.RESERVATION_NOT_FOUND));

        return ReservationMapper.INSTANCE.reservationToReservationResponse(reservation);
    }

    @Transactional
    public void deleteReservation(Long reservationId) {
        reservationWebRepository.delete(reservationId);
    }
}
