package nextstep.domain.dto;

import nextstep.domain.reservation.Reservation;
import nextstep.domain.theme.Theme;

public class ReservationResponse {
    private final Long id;
    private final String date;
    private final String time;
    private final String name;
    private final String themeName;
    private final String themeDesc;
    private final Integer themePrice;

    public ReservationResponse(Reservation reservation, Theme theme) {
        this.id = reservation.getId();
        this.date = reservation.getDate().toString();
        this.time = reservation.getTime().toString();
        this.name = reservation.getName();
        this.themeName = theme.getName();
        this.themeDesc = theme.getDesc();
        this.themePrice = theme.getPrice();
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
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
