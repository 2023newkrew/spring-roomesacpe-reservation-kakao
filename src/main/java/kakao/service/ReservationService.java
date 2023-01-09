package kakao.service;

import kakao.domain.Reservation;
import kakao.dto.request.CreateReservationRequest;
import kakao.repository.ReservationRepository;
import kakao.repository.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ThemeRepository themeRepository;

    public ReservationService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public void createReservation(CreateReservationRequest request) {
        Reservation reservation = new Reservation(request.date, request.time, request.name, themeRepository.theme);
        reservationRepository.save(reservation);
    }
}
