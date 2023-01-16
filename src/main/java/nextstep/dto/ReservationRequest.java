package nextstep.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    private LocalDate date;
    private LocalTime time;
    private String name;
    @JsonProperty("theme_id")
    private long themeId;

    public ReservationRequest() {
    }

    public ReservationRequest(LocalDate date, LocalTime time, String name, long themeId) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
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

    public long getThemeId(){
        return themeId;
    }
}
