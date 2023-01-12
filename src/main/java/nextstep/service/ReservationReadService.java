package nextstep.service;

import nextstep.domain.reservation.Reservation;
import nextstep.domain.reservation.repository.ReservationRepository;
import nextstep.dto.response.FindReservationResponse;
import nextstep.error.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static nextstep.error.ErrorType.RESERVATION_NOT_FOUND;

@Service
public class ReservationReadService {

    private final ReservationRepository reservationRepository;

    public ReservationReadService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional(readOnly = true)
    public FindReservationResponse findReservationById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ApplicationException(RESERVATION_NOT_FOUND));

        return FindReservationResponse.from(reservation);
    }

    @Transactional(readOnly = true)
    public boolean existsByThemeId(Long themeId) {
        return reservationRepository.existsByThemeId(themeId);
    }

}
