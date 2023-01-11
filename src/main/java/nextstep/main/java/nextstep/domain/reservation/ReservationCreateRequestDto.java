package nextstep.main.java.nextstep.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class ReservationCreateRequestDto {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

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
