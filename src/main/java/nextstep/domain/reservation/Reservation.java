package nextstep.domain.reservation;

import nextstep.domain.theme.Theme;

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
        this(null, date, time, name, theme);
    }

    public Reservation(Long id, Reservation reservation) {
        this(id, reservation.getDate(), reservation.getTime(), reservation.getName(), reservation.getTheme());
    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    @Deprecated
    public boolean isSameDateAndTime(LocalDate date, LocalTime time) {
        return this.date.equals(date) && this.time.equals(time);
    }

    @Deprecated
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

    @Deprecated
    public void setId(Long id) {
        this.id = id;
    }
}
