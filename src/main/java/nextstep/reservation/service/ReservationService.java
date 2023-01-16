package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.dto.ReservationResponse;
import nextstep.reservation.dto.ThemeResponse;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.exception.RoomEscapeException;
import nextstep.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static nextstep.reservation.constant.RoomEscapeConstant.ENTITY_DELETE_NUMBER;
import static nextstep.reservation.exception.RoomEscapeExceptionCode.DUPLICATE_TIME_RESERVATION;
import static nextstep.reservation.exception.RoomEscapeExceptionCode.NO_SUCH_RESERVATION;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ThemeService themeService;

    public ReservationResponse registerReservation(ReservationRequest reservationRequest) {
        if (reservationRepository.findByDateAndTime(reservationRequest.getDate(), reservationRequest.getTime()).size() > 0) {
            throw new RoomEscapeException(DUPLICATE_TIME_RESERVATION);
        }

        Reservation savedReservation = reservationRepository.save(reservationRequest.toEntity());
        ThemeResponse foundedTheme = themeService.findById(savedReservation.getThemeId());
        return ReservationResponse.from(savedReservation, foundedTheme);
    }

    @Transactional(readOnly = true)
    public ReservationResponse findById(long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RoomEscapeException(NO_SUCH_RESERVATION));
        ThemeResponse theme = themeService.findById(reservation.getThemeId());
        return ReservationResponse.from(reservation, theme);
    }

    public Boolean delete(long id) {
        int deleteRowNumber = reservationRepository.deleteById(id);
        return deleteRowNumber == ENTITY_DELETE_NUMBER;
    }

    public void clear() {
        reservationRepository.clear();
    }
}
