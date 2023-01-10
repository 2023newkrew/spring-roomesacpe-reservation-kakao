package nextstep.domain.reservation;

import nextstep.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation {

    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Theme theme;

    public Reservation(Long id, String date, String time, String name, Theme theme) {
        this.id = id;
        this.date = LocalDate.parse(date);
        this.time = LocalTime.parse(time);
        this.name = name;
        this.theme = theme;
    }

    public Reservation(LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = null;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
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

    public Theme getTheme() {
        return theme;
    }

    @Override
    public boolean equals(Object obj) {
        Reservation reservation = (Reservation) obj;
        return this.name.equals(reservation.getName())
        && this.date.equals(reservation.getDate())
        && this.time.equals(reservation.getTime())
        && this.theme.equals(reservation.getTheme());
    }
}
