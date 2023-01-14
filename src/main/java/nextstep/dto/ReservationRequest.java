package nextstep.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @DateTimeFormat(pattern = "hh:mm")
    private LocalTime time;
    private String name;
    @JsonProperty("theme_id")
    private Long themeId;

    public ReservationRequest(LocalDate date, LocalTime time, String name, Long themeId) {
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

    public Long getThemeId() {
        return themeId;
    }
}
