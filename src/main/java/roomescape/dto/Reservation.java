package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private long theme_id;

    public Reservation() {}

    public Reservation(LocalDate date, LocalTime time, String name, long theme_id) {
        this(null, date, time, name, theme_id);
    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name, long theme_id) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme_id = theme_id;
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

    public long getThemeId() {
        return theme_id;
    }
}
