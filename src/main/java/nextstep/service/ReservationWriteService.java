package nextstep.service;

import nextstep.domain.reservation.Reservation;
import nextstep.domain.reservation.repository.ReservationRepository;
import nextstep.domain.theme.Theme;
import nextstep.dto.request.CreateReservationRequest;
import nextstep.error.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static nextstep.error.ErrorType.DUPLICATE_RESERVATION;

@Service
public class ReservationWriteService {

    private final ReservationRepository reservationRepository;
    private final ThemeReadService themeReadService;

    public ReservationWriteService(ReservationRepository reservationRepository, ThemeReadService themeReadService) {
        this.reservationRepository = reservationRepository;
        this.themeReadService = themeReadService;
    }

    @Transactional
    public Long createReservation(CreateReservationRequest createReservationRequest) {
        LocalDate date = createReservationRequest.parseDate();
        LocalTime time = createReservationRequest.parseTime();
        Theme theme = themeReadService.findThemeByName(createReservationRequest.getThemeName());

        if (reservationRepository.existsByThemeIdAndDateAndTime(theme.getId(), date, time)) {
            throw new ApplicationException(DUPLICATE_RESERVATION);
        }

        Reservation savedReservation = reservationRepository.save(new Reservation(date, time, createReservationRequest.getName(), theme));
        return savedReservation.getId();
    }

    @Transactional
    public void deleteReservationById(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
