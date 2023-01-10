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

    public static Long maxId = 1L;

    public Reservation(String date, String time, String name, Theme theme) {
        this.id = maxId++;
        DateTimeFormatter localDateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter localTimeformatter = DateTimeFormatter.ofPattern("HH:mm");
        this.date = LocalDate.parse(date, localDateformatter);
        this.time = LocalTime.parse(time, localTimeformatter);
        this.name = name;
        this.theme = theme;
    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = maxId++;
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
}
