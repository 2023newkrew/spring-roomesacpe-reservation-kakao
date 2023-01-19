package nextstep.dto;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class FindReservation {
    private final long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final String themeName;
    private final String themeDesc;
    private final int themePrice;

    public FindReservation(long id, LocalDate date, LocalTime time, String name, String themeName, String themeDesc, int themePrice) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeName = themeName;
        this.themeDesc = themeDesc;
        this.themePrice = themePrice;
    }

    public long getId() {
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

    public int getThemePrice() {
        return themePrice;
    }

    public static FindReservation from(Reservation reservation, Theme theme) {
        return new FindReservation(
                reservation.getId(),
                reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                theme.getName(),
                theme.getDesc(),
                theme.getPrice()
        );
    }
}
