package roomescape.service;

import roomescape.repository.ReservationRepository;
import roomescape.repository.ThemeRepository;
import roomescape.domain.Reservation;
import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.dto.ReservationCreateRequest;

import java.util.NoSuchElementException;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Reservation createReservation(ReservationCreateRequest reservationCreateRequest) {
        if (reservationRepository.checkSchedule(reservationCreateRequest.getDate(), reservationCreateRequest.getTime()) != 0) {
            throw new IllegalArgumentException("중복된 예약 발생");
        }
        Theme theme = themeRepository.findThemeById(reservationCreateRequest.getThemeId());
        Long id = reservationRepository.addReservation(reservationCreateRequest.toReservation(theme));
        return reservationRepository.findReservation(id).orElseThrow(NoSuchElementException::new);
    }

    public Reservation showReservation(Long id) {
        Reservation reservation = reservationRepository.findReservation(id).orElseThrow(NoSuchElementException::new);
        return reservation;
    }

    public int deleteReservation(Long id) {
        return reservationRepository.removeReservation(id);
    }

}
