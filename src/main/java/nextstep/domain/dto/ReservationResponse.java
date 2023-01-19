package nextstep.domain.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationResponse {
    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final ThemeResponse theme;

    public ReservationResponse(Long id, LocalDate date, LocalTime time, String name, ThemeResponse theme) {
        this.id = id;
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

    public ThemeResponse getTheme() {
        return theme;
    }

    public Long getId() {
        return id;
    }
}
