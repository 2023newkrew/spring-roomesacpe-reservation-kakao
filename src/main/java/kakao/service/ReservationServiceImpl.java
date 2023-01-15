package kakao.service;

import kakao.exception.DuplicatedReservationException;
import kakao.exception.ReservationNotFoundException;
import kakao.controller.request.ReservationRequest;
import kakao.controller.response.ReservationResponse;
import kakao.repository.ReservationRepository;
import kakao.repository.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Long book(ReservationRequest reservationRequest) {
        if (reservationRepository.findByDateAndTime(reservationRequest.getDate(), reservationRequest.getTime()).isEmpty()) {
            throw new DuplicatedReservationException();
        }

        return reservationRepository.create(reservationRequest, ThemeRepository.DEFAULT_THEME);
    }

    public ReservationResponse lookUp(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(ReservationNotFoundException::new);
    }

    public void cancel(Long id) {
        reservationRepository.deleteById(id);
    }
}
