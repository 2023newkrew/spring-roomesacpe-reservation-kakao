package roomescape.service;

import org.springframework.transaction.annotation.Transactional;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ThemeRepository;
import roomescape.domain.Reservation;
import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.dto.ReservationRequest;

import java.util.NoSuchElementException;

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
        if (reservationRepository.checkSchedule(reservationRequest.getDate(), reservationRequest.getTime()) != 0) {
            throw new IllegalArgumentException("중복된 예약 발생");
        }
        Theme theme = themeRepository.findThemeById(reservationRequest.getTheme_id());
        Long id = reservationRepository.addReservation(reservationRequest.toReservation(theme));
        return reservationRepository.findReservation(id);
    }

    public Reservation showReservation(Long id) {
        Reservation reservation = reservationRepository.findReservation(id);
        if (reservation == null) {
            throw new NoSuchElementException("없는 예약 조회");
        }
        return reservation;
    }

    public int deleteReservation(Long id) {
        return reservationRepository.removeReservation(id);
    }

}
