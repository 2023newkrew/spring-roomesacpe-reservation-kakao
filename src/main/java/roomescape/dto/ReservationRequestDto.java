package roomescape.dto;

import roomescape.model.Reservation;
import roomescape.model.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequestDto {
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Long themeId;

    public ReservationRequestDto() {}

    public ReservationRequestDto(LocalDate date, LocalTime time, String name, Long themeId) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }

    public Reservation toEntity(Theme theme) {
        return new Reservation(null, date, time, name, theme);
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
}
