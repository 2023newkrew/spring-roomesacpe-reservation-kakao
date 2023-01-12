package nextstep.domain.reservation;

import nextstep.domain.JdbcEntity;
import nextstep.domain.theme.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@JdbcEntity
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
        this(id, reservation.date, reservation.time, reservation.name, reservation.theme);
    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    @Deprecated
    public boolean isSameThemeAndDateAndTime(Long themeId, LocalDate date, LocalTime time) {
        return Objects.equals(theme.getId(), themeId) && this.date.equals(date) && this.time.equals(time);
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
