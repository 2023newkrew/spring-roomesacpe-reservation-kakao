package nextstep.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {

    private final String name;
    private final LocalDate date;
    private final LocalTime time;
    private final Long themeId;

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
