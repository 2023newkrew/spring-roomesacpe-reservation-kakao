package nextstep.domain;

import nextstep.utils.AutoIncrementGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Theme theme;

    public Reservation(LocalDate date, LocalTime time, String name, Theme theme) {
        this(AutoIncrementGenerator.getId(Reservation.class), date, time, name, theme);
    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    public boolean isSameDateAndTime(LocalDate date, LocalTime time) {
        return this.date.equals(date) && this.time.equals(time);
    }

    public boolean isSameId(Long id) {
        return Objects.equals(this.id, id);
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
