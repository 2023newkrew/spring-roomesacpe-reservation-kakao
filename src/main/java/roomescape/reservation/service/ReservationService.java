package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.TimeTable;
import roomescape.reservation.exception.DuplicatedReservationException;
import roomescape.reservation.exception.InvalidTimeReservationException;
import roomescape.reservation.exception.NoSuchReservationException;
import roomescape.reservation.exception.NoSuchThemeException;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.theme.repository.ThemeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Long createReservation(ReservationRequest reservation) {
        checkDuplicatedDateAndTime(reservation.getDate(), reservation.getTime());
        checkInvalidTime(reservation.getTime());
        checkExistenceOfTheme(reservation.getThemeId());
        return reservationRepository.save(reservation);
    }

    private void checkDuplicatedDateAndTime(LocalDate date, LocalTime time) {
        Optional<Reservation> reservation = reservationRepository.findDuplicatedDateAndTime(date, time);
        if (reservation.isPresent()) {
            throw new DuplicatedReservationException("같은 날짜/시간에 이미 예약이 있습니다.");
        }
    }

    private void checkInvalidTime(LocalTime time) {
        if (!isInTimeTable(time)) {
            throw new InvalidTimeReservationException("유효하지 않은 시간입니다.");
        }
    }

    public boolean isInTimeTable(LocalTime time) {
        for (TimeTable validTime : TimeTable.values()) {
            if (validTime.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }

    private void checkExistenceOfTheme(Long themeId) {
        if (themeRepository.findById(themeId).isEmpty()) {
            throw new NoSuchThemeException("해당 테마는 존재하지 않습니다.");
        }
    }

    public ReservationResponse findById(String reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        checkExistenceOfReservation(reservation);
        return ReservationResponse.of(reservation.get());
    }

    private void checkExistenceOfReservation(Optional<Reservation> reservation) {
        if (reservation.isEmpty()) {
            throw new NoSuchReservationException("id에 해당하는 예약이 존재하지 않습니다.");
        }
    }

    public void deleteById(String reservationId) {
        checkExistenceOfReservation(reservationRepository.findById(reservationId));
        reservationRepository.deleteById(reservationId);
    }
}
