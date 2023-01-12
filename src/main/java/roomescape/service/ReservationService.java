package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;
import roomescape.model.Theme;
import roomescape.repository.*;

import java.util.NoSuchElementException;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationService(ReservationJdbcRepository reservationRepository, ThemeJdbcRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Long createReservation(ReservationRequestDto reservationRequest) {
        Theme theme = themeRepository
                .findOneById(reservationRequest.getThemeId())
                .orElseThrow(() -> {throw new NoSuchElementException("No Theme by that Id");});
        checkForOverlappingReservation(reservationRequest);
        return reservationRepository.save(reservationRequest.toEntity(theme));
    }

    public ReservationResponseDto findReservation(Long reservationId) {
        Reservation reservation = reservationRepository
                .findOneById(reservationId)
                .orElseThrow(() -> {throw new NoSuchElementException("No Reservation by that Id");});
        return new ReservationResponseDto(reservation);
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.delete(reservationId);
    }

    private void checkForOverlappingReservation(ReservationRequestDto reservationRequest) {
        if (reservationRepository.hasOneByDateAndTimeAndTheme(
                reservationRequest.getDate(),
                reservationRequest.getTime(),
                reservationRequest.getThemeId())
        ) {
            throw new IllegalArgumentException("Already have reservation for that theme at that date & time");
        }
    }
}
