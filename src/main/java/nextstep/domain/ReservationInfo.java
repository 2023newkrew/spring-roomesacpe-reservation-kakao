package nextstep.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationInfo {
    private Long id;
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    private String name;
    private String themeName;
    private String themeDesc;
    private Integer themePrice;

    public ReservationInfo() {
    }

    public ReservationInfo(Reservation reservation) {
        this.id = reservation.getId();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
        this.name = reservation.getName();
        this.themeName = reservation.getTheme().getName();
        this.themeDesc = reservation.getTheme().getDesc();
        this.themePrice = reservation.getTheme().getPrice();
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
