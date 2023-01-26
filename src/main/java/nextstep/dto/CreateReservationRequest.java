package nextstep.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationRequest {
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Long themeId;

    public CreateReservationRequest(LocalDate date, LocalTime time, String name, Long themeId) {
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

    @Override
    public String toString() {
        return "CreateReservationRequest{" +
                "date=" + date +
                ", time=" + time +
                ", name='" + name + '\'' +
                ", themeId=" + themeId +
                '}';
    }
}
