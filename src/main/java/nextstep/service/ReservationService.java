package nextstep.service;

import nextstep.domain.reservation.Reservation;
import nextstep.domain.reservation.repository.ReservationRepository;
import nextstep.domain.theme.repository.ThemeRepository;
import nextstep.dto.request.CreateReservationRequest;
import nextstep.dto.response.FindReservationResponse;
import nextstep.error.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static nextstep.error.ErrorType.DUPLICATE_RESERVATION;
import static nextstep.error.ErrorType.RESERVATION_NOT_FOUND;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Long createReservation(CreateReservationRequest createReservationRequest) {
        LocalDate date = LocalDate.parse(createReservationRequest.getDate());
        LocalTime time = LocalTime.parse(createReservationRequest.getTime());

        if (reservationRepository.existsByDateAndTime(date, time)) {
            throw new ApplicationException(DUPLICATE_RESERVATION);
        }

        Reservation savedReservation = reservationRepository.save(new Reservation(date, time, createReservationRequest.getName(), ThemeRepository.getDefaultTheme()));
        return savedReservation.getId();
    }

    @Transactional(readOnly = true)
    public FindReservationResponse findReservationById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ApplicationException(RESERVATION_NOT_FOUND));

        return FindReservationResponse.from(reservation);
    }

    @Transactional
    public void deleteReservationById(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
