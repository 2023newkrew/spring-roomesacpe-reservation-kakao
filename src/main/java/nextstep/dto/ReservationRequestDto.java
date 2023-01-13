package nextstep.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class ReservationRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    private String name;

    public ReservationRequestDto() {
    }

    public ReservationRequestDto(LocalDate date, LocalTime time, String name) {
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationRequestDto that = (ReservationRequestDto) o;
        return Objects.equals(date, that.date) && Objects.equals(time, that.time) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, name);
    }

    @Override
    public String toString() {
        return "ReservationRequestDto{" +
                "date=" + date +
                ", time=" + time +
                ", name='" + name + '\'' +
                '}';
    }
}
