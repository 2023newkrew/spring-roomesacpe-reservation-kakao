package reservation.domain.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDto {

    private LocalDate date;
    private LocalTime time;
    private String name;

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
