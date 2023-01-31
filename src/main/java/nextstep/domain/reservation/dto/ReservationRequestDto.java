package nextstep.domain.reservation.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequestDto {

    private final Long themeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;
    @DateTimeFormat(pattern = "hh:mm")
    private final LocalTime time;
    private final String name;

    public ReservationRequestDto(Long themeId, LocalDate date, LocalTime time, String name) {
        this.themeId = themeId;
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public Long getThemeId() {
        return themeId;
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
}
