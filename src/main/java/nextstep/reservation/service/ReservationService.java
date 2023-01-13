package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.dto.ReservationResponse;
import nextstep.reservation.dto.ThemeResponse;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.exception.ReservationException;
import nextstep.reservation.exception.ReservationExceptionCode;
import nextstep.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static nextstep.reservation.exception.ReservationExceptionCode.DUPLICATE_TIME_RESERVATION;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ThemeService themeService;

    public ReservationResponse registerReservation(ReservationRequest reservationRequest) {
        if (reservationRepository.findByDateAndTime(reservationRequest.getDate(), reservationRequest.getTime()).size() > 0) {
            throw new ReservationException(DUPLICATE_TIME_RESERVATION);
        }

        Reservation savedReservation = reservationRepository.save(reservationRequest.toEntity());
        ThemeResponse foundedTheme = themeService.findById(savedReservation.getThemeId());
        return ReservationResponse.from(savedReservation, foundedTheme);
    }

    @Transactional(readOnly = true)
    public ReservationResponse findById(long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isEmpty()) {
            throw new ReservationException(ReservationExceptionCode.NO_SUCH_RESERVATION);
        }
        Reservation reservation = optionalReservation.get();
        ThemeResponse theme = themeService.findById(reservation.getThemeId());
        return ReservationResponse.from(reservation, theme);
    }

    public Boolean delete(long id) {
        int deleteRowNumber = reservationRepository.deleteById(id);
        return deleteRowNumber == 1;
    }

    public void clear() {
        reservationRepository.clear();
    }
}
