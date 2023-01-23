package roomescape.reservation.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {

    private LocalDate date;
    private LocalTime time;
    private String name;
    private long themeId;

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Long getThemeId() {
        return themeId;
    }

    public Reservation toEntity() {
        return Reservation.of(0L, date, time, name, themeId);
    }
}