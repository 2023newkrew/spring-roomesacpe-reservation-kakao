package nextstep.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDTO {
    private LocalDate date;
    private LocalTime time;
    private String name;

    public ReservationDTO() {
    }

    public ReservationDTO(LocalDate date, LocalTime time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
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
