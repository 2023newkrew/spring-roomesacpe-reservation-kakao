package roomescape.dto;

import roomescape.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Theme theme;

    public ReservationRequest(LocalDate date, LocalTime time, String name, Theme theme) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

}