package nextstep.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import nextstep.model.Reservation;
import nextstep.model.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationResponse {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private String themeName;
    private String themeDesc;
    private Integer themePrice;

    public ReservationResponse(Reservation reservation, Theme theme) {
        this(reservation.getId(), reservation.getDate(), reservation.getTime(), reservation.getName(),
                theme.getName(), theme.getDesc(), theme.getPrice());
    }

    @JsonCreator
    public ReservationResponse(Long id, LocalDate date, LocalTime time, String name, String themeName, String themeDesc, Integer themePrice) {
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
