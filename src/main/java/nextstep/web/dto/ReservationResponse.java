package nextstep.web.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.model.Reservation;

public class ReservationResponse {
    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final String themeName;
    private final String themeDesc;
    private final Integer themePrice;

    public ReservationResponse(Reservation reservation) {
        this(reservation.getId(), reservation.getDate(), reservation.getTime(), reservation.getName(),
                reservation.getTheme().getName(), reservation.getTheme().getDesc(), reservation.getTheme().getPrice());
    }

    public ReservationResponse(Long id, LocalDate date, LocalTime time, String name, String themeName, String themeDesc,
                               Integer themePrice) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeName = themeName;
        this.themeDesc = themeDesc;
        this.themePrice = themePrice;
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
