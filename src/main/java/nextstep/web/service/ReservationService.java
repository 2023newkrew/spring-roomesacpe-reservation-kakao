package nextstep.web.service;

import nextstep.domain.ReservationManager;
import nextstep.domain.repository.ReservationRepository;
import nextstep.domain.repository.ThemeRepository;
import nextstep.dto.CreateReservationRequest;
import nextstep.dto.FindReservationResponse;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationManager reservationManager;

    public ReservationService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationManager = new ReservationManager(reservationRepository, themeRepository);
    }

    public Long createReservation(CreateReservationRequest createReservationRequest) {
        return reservationManager.createReservation(createReservationRequest);
    }

    public FindReservationResponse findReservationById(Long reservationId) {
        return reservationManager.findReservationById(reservationId);
    }

    public boolean deleteReservationById(Long reservationId) {
        return reservationManager.deleteReservationById(reservationId);
    }
}
