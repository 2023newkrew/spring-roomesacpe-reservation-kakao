package nextstep.dto;

import nextstep.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationRequest {
    private LocalDate date;
    private LocalTime time;
    private String name;

    public CreateReservationRequest(LocalDate date, LocalTime time, String name) {
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
