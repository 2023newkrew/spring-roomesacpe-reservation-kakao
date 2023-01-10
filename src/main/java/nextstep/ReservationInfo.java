package nextstep;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationInfo {
    private Long id;
    private LocalDate date;
    @JsonFormat(pattern="HH:mm")
    private LocalTime time;
    private String name;
    private String themeName;
    private String themeDesc;
    private Integer themePrice;

    public ReservationInfo() {
    }

    public ReservationInfo(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
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
