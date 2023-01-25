package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.ReservationCreateRequest;
import nextstep.exception.DuplicateReservationException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.exception.ThemeNotFoundException;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;


    public ReservationService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Reservation save(ReservationCreateRequest request) {
        Theme theme = themeRepository.findById(request.getThemeId()).orElseThrow(ThemeNotFoundException::new);
        Reservation reservation = request.toReservation(theme);
        if (reservationRepository.hasReservationAt(reservation.getDate(), reservation.getTime().getHour())) {
            throw new DuplicateReservationException();
        }
        return reservationRepository.save(reservation);
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new);
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}
