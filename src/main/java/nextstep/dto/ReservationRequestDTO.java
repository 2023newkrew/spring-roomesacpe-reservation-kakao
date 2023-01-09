package nextstep.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequestDTO {

    private final LocalDate date;

    private final LocalTime time;

    private final String name;

    public ReservationRequestDTO(LocalDate date, LocalTime time, String name) {
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
