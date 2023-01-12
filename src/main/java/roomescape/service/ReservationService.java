package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;
import roomescape.model.Theme;
import roomescape.repository.ReservationJdbcRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ThemeRepository;

import java.util.NoSuchElementException;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationService(ReservationJdbcRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Reservation createReservation(ReservationRequestDto req) {
        Theme theme = themeRepository
                .find(1L)
                .orElseThrow(() -> {throw new NoSuchElementException("No Theme by that Name");});
        if (reservationRepository.has(req.getDate(), req.getTime())) {
            throw new IllegalArgumentException("Already have reservation at that date & time");
        }
        Reservation reservation = new Reservation(req, theme);
        Long id = reservationRepository.save(reservation);
        reservation.setId(id);
        return reservation;
    }

    public ReservationResponseDto findReservation(Long id) {
        Reservation reservation = reservationRepository
                .find(id)
                .orElseThrow(() -> {throw new NoSuchElementException("No Reservation by that Id");});
        return new ReservationResponseDto(reservation);
    }

    public void cancelReservation(Long id) {
        reservationRepository.delete(id);
    }
}
