package roomescape.service;

import org.springframework.transaction.annotation.Transactional;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ThemeRepository;
import roomescape.domain.Reservation;
import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.dto.ReservationRequest;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        if (reservationRepository.checkSchedule(reservationRequest) == 0) {
            Theme theme = themeRepository.findThemeById(reservationRequest.getTheme_id());
            Long id = reservationRepository.addReservation(reservationRequest.toReservation(theme));
//            return id;
            return reservationRepository.findReservation(id);
        }
        return null;
    }

    public Reservation showReservation(Long id) {
        return reservationRepository.findReservation(id);
    }

    public int deleteReservation(Long id) {
        return reservationRepository.removeReservation(id);
    }

}
