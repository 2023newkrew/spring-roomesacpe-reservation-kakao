package kakao.service;

import java.time.LocalDate;
import java.time.LocalTime;
import kakao.domain.Reservation;
import kakao.domain.Theme;
import kakao.dto.request.CreateReservationRequest;
import kakao.dto.response.ReservationResponse;
import kakao.error.ErrorCode;
import kakao.error.exception.DuplicatedReservationException;
import kakao.error.exception.RecordNotFoundException;
import kakao.repository.ReservationRepository;
import kakao.repository.ThemeJDBCRepository;
import kakao.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ThemeRepository themeRepository;

    public Reservation createReservation(CreateReservationRequest request) {
        checkDuplicatedReservation(request.date, request.time);
        Theme theme = getExistTheme(request.themeId);
        return reservationRepository.save(Reservation.builder()
                .date(request.date)
                .time(request.time)
                .name(request.name)
                .theme(theme)
                .build()
        );
    }

    @Transactional(readOnly = true)
    public ReservationResponse getReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        if (Objects.isNull(reservation)) {
            throw new RecordNotFoundException(ErrorCode.RESERVATION_NOT_FOUND);
        }
        return new ReservationResponse(reservation);
    }

    public int deleteReservation(Long id) {
        return reservationRepository.delete(id);
    }

    private void checkDuplicatedReservation(LocalDate date, LocalTime time) {
        boolean isDuplicate = reservationRepository.findByDateAndTime(date, time).size() > 0;
        if (isDuplicate) {
            throw new DuplicatedReservationException(ErrorCode.DUPLICATE_RESERVATION);
        }
    }

    private Theme getExistTheme(Long themeId) {
        Theme theme = themeRepository.findById(themeId);
        if (Objects.isNull(theme)) {
            throw new RecordNotFoundException(ErrorCode.THEME_NOT_FOUND);
        }
        return theme;
    }
}
