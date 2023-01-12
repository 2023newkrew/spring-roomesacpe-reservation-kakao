package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;
import roomescape.model.Theme;
import roomescape.repository.ReservationJdbcRepository;
import roomescape.repository.ReservationRepository;

import java.util.NoSuchElementException;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ThemeService themeService;


    public ReservationService(ReservationJdbcRepository reservationRepository, ThemeService themeService) {
        this.reservationRepository = reservationRepository;
        this.themeService = themeService;
    }

    public ReservationResponseDto createReservation(ReservationRequestDto req) {
        if (reservationRepository.has(req.getDate(), req.getTime())) {
            throw new IllegalArgumentException("Already have reservation at that date & time");
        }
        Reservation reservation = new Reservation(req);
        Long id = reservationRepository.save(reservation);
        reservation.setId(id);
        Theme theme = themeService.getTheme(reservation.getThemeId());
        return new ReservationResponseDto(reservation, theme);
    }

    public ReservationResponseDto findReservation(Long id) {
        Reservation reservation = getReservation(id);
        Theme theme = themeService.getTheme(reservation.getThemeId());
        return new ReservationResponseDto(reservation, theme);
    }

    public Boolean cancelReservation(Long id) {
        return reservationRepository.delete(id) == 1;
    }

    public Reservation getReservation(Long id) {
        return reservationRepository
                .find(id)
                .orElseThrow(() -> {throw new NoSuchElementException("No Reservation by that ID");});
    }

    public ReservationResponseDto getReservationDto(Reservation reservation) {
        Theme theme = themeService.getTheme(reservation.getThemeId());
        return new ReservationResponseDto(reservation, theme);
    }
}
