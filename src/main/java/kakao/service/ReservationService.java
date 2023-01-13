package kakao.service;

import java.time.LocalDate;
import java.time.LocalTime;
import kakao.domain.Reservation;
import kakao.domain.Theme;
import kakao.dto.request.CreateReservationRequest;
import kakao.dto.response.ReservationResponse;
import kakao.error.ErrorCode;
import kakao.error.exception.RoomReservationException;
import kakao.repository.reservation.ReservationRepository;
import kakao.repository.theme.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ThemeUtilService themeUtilService;

    public ReservationResponse createReservation(CreateReservationRequest request) {
        checkDuplicatedReservation(request.getThemeId(), request.getDate(), request.getTime());
        Theme theme = themeUtilService.getThemeById(request.getThemeId());
        return new ReservationResponse(reservationRepository.save(Reservation.builder()
                .date(request.getDate())
                .time(request.getTime())
                .name(request.getName())
                .theme(theme)
                .build())
        );
    }

    @Transactional(readOnly = true)
    public ReservationResponse getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        if (Objects.isNull(reservation)) {
            throw new RoomReservationException(ErrorCode.RESERVATION_NOT_FOUND);
        }
        return new ReservationResponse(reservation);
    }

    public int deleteReservationById(Long id) {
        return reservationRepository.delete(id);
    }

    private void checkDuplicatedReservation(Long themeId, LocalDate date, LocalTime time) {
        boolean isDuplicate = reservationRepository.findByThemeIdAndDateAndTime (themeId, date, time).size() > 0;
        if (isDuplicate) {
            throw new RoomReservationException(ErrorCode.DUPLICATE_RESERVATION);
        }
    }
}
