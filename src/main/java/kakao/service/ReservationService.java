package kakao.service;

import kakao.model.request.ReservationRequest;
import kakao.model.response.ReservationResponse;
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

    public Long createReservation(ReservationRequest reservationRequest) {
        int numberOfBooked = reservationRepository
                .findByDateAndTime(reservationRequest.getDate(), reservationRequest.getTime())
                .size();

        if (numberOfBooked > 0) {
            throw new RuntimeException("Duplicated reservation.");
        }

        return reservationRepository.create(reservationRequest, ThemeRepository.DEFAULT_THEME);
    }

    public ReservationResponse getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found."));
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
