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

    public Long createReservation(ReservationRequestDto reservationRequest) {
        Theme theme = themeRepository
                .findOneByName("워너고홈")
                .orElseThrow(() -> {throw new NoSuchElementException("No Theme by that Name");});
        if (reservationRepository.hasOneByDateAndTime(reservationRequest.getDate(), reservationRequest.getTime())) {
            throw new IllegalArgumentException("Already have reservation at that date & time");
        }
        return reservationRepository.save(new Reservation(reservationRequest, theme));
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
}
