package nextstep.main.java.nextstep.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class ReservationCreateRequestDto {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    public ReservationCreateRequestDto(LocalDate date, LocalTime time, String name) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationCreateRequestDto that = (ReservationCreateRequestDto) o;
        return Objects.equals(date, that.date) && Objects.equals(time, that.time) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, name);
    }
}
