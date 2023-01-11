package nextstep.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Long themeId;

    public Reservation(LocalDate date, LocalTime time, String name, Long themeId) {
        this(null, date, time, name, themeId);
    }

    public Reservation(Long id, Reservation reservation) {
        this(id, reservation.date, reservation.time, reservation.name, reservation.themeId);
    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Long themeId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
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

    public Long getThemeId() {
        return themeId;
    }

    @Deprecated
    public void setId(Long id) {
        this.id = id;
    }
}
