package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Long theme_id;

    public ReservationRequest(LocalDate date, LocalTime time, String name, Long theme_id) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme_id = theme_id;
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

    public Long getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(Long theme_id) {
        this.theme_id = theme_id;
    }
}