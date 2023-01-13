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
    private final String themeName;
    private final String themeDesc;
    private final Integer themePrice;

    public ReservationResponseDto(Reservation reservation, Theme theme) {
        this.id = reservation.getId();
        this.date = reservation.getDateTime().toLocalDate();
        this.time = reservation.getDateTime().toLocalTime();
        this.name = reservation.getName();
        this.themeName = theme.getName();
        this.themeDesc = theme.getDesc();
        this.themePrice = theme.getPrice();
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
        return themeName;
    }

    public String getThemeDesc() {
        return themeDesc;
    }

    public Integer getThemePrice() {
        return themePrice;
    }
}
