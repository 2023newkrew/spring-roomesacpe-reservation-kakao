package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;
import roomescape.model.Theme;
import roomescape.repository.*;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    @Transactional
    public Long createReservation(ReservationRequestDto reservationRequest) {
        Theme theme = themeRepository
                .findOneById(reservationRequest.getThemeId())
                .orElseThrow(() -> new NoSuchElementException("No Theme by that Id"));
        checkForOverlappingReservation(reservationRequest);
        return reservationRepository.save(reservationRequest.toEntity(theme));
    }

    public ReservationResponseDto findReservation(Long reservationId) {
        Reservation reservation = reservationRepository
                .findOneById(reservationId)
                .orElseThrow(() -> new NoSuchElementException("No Reservation by that Id"));
        return new ReservationResponseDto(reservation);
    }

    @Transactional
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
