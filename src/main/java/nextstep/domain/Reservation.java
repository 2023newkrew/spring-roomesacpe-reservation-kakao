package nextstep.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Long themeId;

    public Reservation() {
        this.id = null;
    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Long themeId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }

    public Reservation(LocalDate date, LocalTime time, String name, Long themeId) {
        this.id = null;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
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
}
