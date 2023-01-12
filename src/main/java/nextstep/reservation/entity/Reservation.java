package nextstep.reservation.entity;

import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    @Nullable
    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Long themeId;

    public Reservation(Long id, LocalDate date, LocalTime time, String name, long theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = theme;
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

    public Long getThemeId() {
        return themeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(date, that.date) && Objects.equals(time, that.time) && Objects.equals(name, that.name) && Objects.equals(themeId, that.themeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time, name, themeId);
    }
}
