package nextstep;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Long theme_id;

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Long theme_id) {
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

    public Long getTheme_id() {
        return theme_id;
    }
}
