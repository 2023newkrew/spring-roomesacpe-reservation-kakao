package nextstep.dto;

import nextstep.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationRequest {
    private LocalDate date;
    private LocalTime time;
    private String name;
    public Long themeId;

    public CreateReservationRequest(LocalDate date, LocalTime time, String name, Long getThemeId) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = getThemeId;
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

    public Long getThemeId() {
        return themeId;
    }

    public Reservation toReservation() {
        return new Reservation(date, time, name, themeId);
    }
}
