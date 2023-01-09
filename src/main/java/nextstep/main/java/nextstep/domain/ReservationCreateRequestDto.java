package nextstep.main.java.nextstep.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationCreateRequestDto {
    LocalDate date;
    LocalTime time;
    String name;

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public ReservationCreateRequestDto(LocalDate date, LocalTime time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
    }
}
