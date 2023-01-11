package roomescape.dto;

import roomescape.model.Reservation;
import roomescape.model.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationResponseDto {
    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Theme theme;

    public ReservationResponseDto(Reservation reservation) {
        this.id = reservation.getId();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
        this.name = reservation.getName();
        this.theme = reservation.getTheme();
    }

    public Long getId() {
        return id;
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

    public String getThemeName() {
        return theme.getName();
    }

    public String getThemeDesc() {
        return theme.getDesc();
    }

    public Integer getThemePrice() {
        return theme.getPrice();
    }
}
