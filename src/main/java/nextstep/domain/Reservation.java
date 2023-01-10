package nextstep.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern="HH:mm")
    private LocalTime time;
    private String name;
    private Theme theme;

    public Reservation() {
    }

    public Reservation(LocalDate date, LocalTime time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public Reservation(LocalDate date, LocalTime time, String name, Theme theme) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = id;
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
