package roomescape.repository;

import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reservations {
    private final static List<Reservation> reservations = new ArrayList<>();
    private static Long reservationIdIndex = 0L;

    private Reservations() {
    }

    public static boolean isNotAvailable(LocalDate date, LocalTime time) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.getDate().isEqual(date) && reservation.getTime().equals(time));
    }

    public static void putReservation(Reservation reservation) {
        reservation.setId(++reservationIdIndex);
        reservations.add(reservation);
    }

    public static Reservation getReservation(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.RESERVATION_NOT_FOUND));
    }

    public static boolean isExist(Long id) {
        try {
            getReservation(id);
            return true;
        } catch (RoomEscapeException e) {
            return false;
        }
    }

    public static void deleteReservation(Long id) {
        Reservation reservation = getReservation(id);
        reservations.remove(reservation);
    }
}
