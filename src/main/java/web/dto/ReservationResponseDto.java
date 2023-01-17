package web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import web.entity.Reservation;
import web.entity.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationResponseDto {

    private Long id;
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    private String name;
    private String themeName;
    private String themeDesc;
    private Integer themePrice;

    private ReservationResponseDto(Long id, LocalDate date, LocalTime time, String name, String themeName, String themeDesc, Integer themePrice) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeName = themeName;
        this.themeDesc = themeDesc;
        this.themePrice = themePrice;
    }

    public static ReservationResponseDto of(long id, Reservation reservation, Theme theme) {
        return new ReservationResponseDto(id, reservation.getDate(), reservation.getTime(),
                reservation.getName(), theme.getName(), theme.getDesc(), theme.getPrice());
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
