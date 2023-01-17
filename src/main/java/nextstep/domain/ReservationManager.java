package nextstep.domain;

import nextstep.domain.repository.ReservationRepository;
import nextstep.domain.repository.ThemeRepository;
import nextstep.dto.CreateReservationRequest;
import nextstep.dto.FindReservationResponse;
import nextstep.exception.DuplicateReservationException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.exception.ThemeNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationManager {
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationManager(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Long createReservation(CreateReservationRequest createReservationRequest) {
        LocalDate date = LocalDate.parse(createReservationRequest.getDate());
        LocalTime time = LocalTime.parse(createReservationRequest.getTime());

        if (reservationRepository.existsByDateAndTime(date, time)) {
            throw new DuplicateReservationException();
        }
        if (themeRepository.findThemeById(createReservationRequest.getThemeId()).isEmpty()) {
            throw new ThemeNotFoundException();
        }

        return reservationRepository.save(Reservation.from(createReservationRequest)).getId();
    }

    public FindReservationResponse findReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(ReservationNotFoundException::new);
        Theme theme = themeRepository.findThemeById(reservation.getThemeId())
                .orElseThrow(ThemeNotFoundException::new);

        return FindReservationResponse.from(reservation, theme);
    }

    public boolean deleteReservationById(Long reservationId) {
        return reservationRepository.deleteById(reservationId);
    }
}
