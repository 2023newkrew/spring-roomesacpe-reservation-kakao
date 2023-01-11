package roomescape.controller.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {

    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    public ReservationRequest(LocalDate date, LocalTime time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Reservation toEntity(Theme theme) {
        return new Reservation(
            date,
            time,
            name,
            theme
        );
    }
}