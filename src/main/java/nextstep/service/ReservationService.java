package nextstep.service;

import nextstep.domain.reservation.Reservation;
import nextstep.domain.reservation.repository.ReservationRepository;
import nextstep.domain.theme.Theme;
import nextstep.dto.request.CreateReservationRequest;
import nextstep.dto.response.FindReservationResponse;
import nextstep.error.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static nextstep.error.ErrorType.DUPLICATE_RESERVATION;
import static nextstep.error.ErrorType.RESERVATION_NOT_FOUND;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ThemeService themeService;

    public ReservationService(ReservationRepository reservationRepository, ThemeService themeService) {
        this.reservationRepository = reservationRepository;
        this.themeService = themeService;
    }

    @Transactional
    public Long createReservation(CreateReservationRequest createReservationRequest) {
        LocalDate date = createReservationRequest.parseDate();
        LocalTime time = createReservationRequest.parseTime();
        Theme theme = themeService.findThemeByName(createReservationRequest.getThemeName());

        if (reservationRepository.existsByDateAndTime(date, time)) {
            throw new ApplicationException(DUPLICATE_RESERVATION);
        }

        Reservation savedReservation = reservationRepository.save(new Reservation(date, time, createReservationRequest.getName(), theme));
        return savedReservation.getId();
    }

    @Transactional(readOnly = true)
    public FindReservationResponse findReservationById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ApplicationException(RESERVATION_NOT_FOUND));

        return FindReservationResponse.from(reservation);
    }

    @Transactional
    public void deleteReservationById(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
