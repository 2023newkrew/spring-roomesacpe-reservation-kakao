package nextstep.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {

    private String name;
    private LocalDate date;
    private LocalTime time;
    private Long themeId;

    public ReservationRequest(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    @JsonCreator
    public ReservationRequest(String name, LocalDate date, LocalTime time, Long themeId) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.themeId = themeId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Long getThemeId() {
        return themeId;
    }
}
