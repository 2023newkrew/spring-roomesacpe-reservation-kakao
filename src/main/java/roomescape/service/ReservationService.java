package roomescape.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.mapper.ReservationMapper;
import roomescape.repository.ReservationWebRepository;
import roomescape.repository.ThemeWebRepository;


import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {
    private final ReservationWebRepository reservationWebRepository;
    private final ThemeWebRepository themeWebRepository;

    public ReservationService(
            ReservationWebRepository reservationWebRepository,
            ThemeWebRepository themeWebRepository
    ) {
        this.reservationWebRepository = reservationWebRepository;
        this.themeWebRepository = themeWebRepository;
    }

    @Transactional
    public Long createReservation(ReservationRequest reservationRequest) {
        LocalDate requestedDate = reservationRequest.getDate();
        LocalTime requestedTime = reservationRequest.getTime();
        Long themeId = reservationRequest.getTheme_id();

        checkTimeDuplication(requestedDate, requestedTime, themeId);
        checkThemeExistence(reservationRequest.getTheme_id());

        return reservationWebRepository.save(
                ReservationMapper.INSTANCE.reservationRequestToReservation(reservationRequest)
        );
    }

    private void checkTimeDuplication(LocalDate date, LocalTime time, Long themeId) {
        reservationWebRepository.findReservationByDateAndTimeAndTheme(date, time, themeId)
                .ifPresent(reservation -> {
                    throw new RoomEscapeException(ErrorCode.DUPLICATED_RESERVATION);
                });
    }

    private void checkThemeExistence(Long theme_id){
        themeWebRepository.findOne(theme_id)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.THEME_NOT_FOUND));
    }

    @Transactional
    public ReservationResponse getReservation(Long reservationId) {
        Reservation reservation = reservationWebRepository.findOne(reservationId)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.RESERVATION_NOT_FOUND));
        Theme theme = themeWebRepository.findOne(reservation.getTheme_id())
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.THEME_NOT_FOUND));

        return ReservationMapper.INSTANCE.reservationToReservationResponse(reservation, theme);
    }

    @Transactional
    public void deleteReservation(Long reservationId) {
        reservationWebRepository.delete(reservationId);
    }
}
