package nextstep.main.java.nextstep.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Theme theme;
    private Long id;

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    public Reservation(Long id, Reservation reservation) {
        this(id, reservation.getDate(), reservation.getTime(), reservation.getName(), reservation.getTheme());
    }

    public Reservation(LocalDate date, LocalTime time, String name, Theme theme) {
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
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", name='" + name + '\'' +
                ", theme=" + theme +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return date.equals(that.date) && time.equals(that.time) && name.equals(that.name) && theme.equals(that.theme) && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, name, theme, id);
    }
}
