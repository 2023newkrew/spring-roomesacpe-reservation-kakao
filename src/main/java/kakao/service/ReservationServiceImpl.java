package kakao.service;

import kakao.exception.DuplicatedReservationException;
import kakao.exception.ReservationNotFoundException;
import kakao.controller.request.ReservationRequest;
import kakao.controller.response.ReservationResponse;
import kakao.exception.ThemeNotFoundException;
import kakao.model.Reservation;
import kakao.model.Theme;
import kakao.repository.ReservationRepository;
import kakao.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
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
        checkIfThemeExist(reservationRequest.getThemeId());
        checkIfDuplicatedReservation(reservationRequest);

        return reservationRepository.create(reservationRequest);
    }

    public ReservationResponse lookUp(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(ReservationNotFoundException::new);
        Theme theme = themeRepository.findById(reservation.getThemeId())
                .orElseThrow(ThemeNotFoundException::new);

        return new ReservationResponse(reservation, theme);
    }

    public void cancel(Long id) {
        reservationRepository.deleteById(id);
    }

    private void checkIfThemeExist(Long themeId) {
        if(themeRepository.findById(themeId).isEmpty()) {
            throw new ThemeNotFoundException();
        }
    }
    private void checkIfDuplicatedReservation(ReservationRequest reservationRequest) {
        if (reservationRepository.findByDateAndTimeAndThemeId(reservationRequest.getDate(), reservationRequest.getTime(), reservationRequest.getThemeId()).isPresent()) {
            throw new DuplicatedReservationException();
        }
    }
}
