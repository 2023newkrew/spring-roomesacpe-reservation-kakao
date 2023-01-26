package nextstep.dto;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public class FindReservation {
    private long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private String themeName;
    private String themeDesc;
    private int themePrice;

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

    @Override
    public String toString() {
        return "FindReservation{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", name='" + name + '\'' +
                ", themeName='" + themeName + '\'' +
                ", themeDesc='" + themeDesc + '\'' +
                ", themePrice=" + themePrice +
                '}';
    }
}
