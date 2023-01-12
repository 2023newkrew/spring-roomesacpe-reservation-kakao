package web.entity;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class Reservation {

    private LocalDate date;
    private LocalTime time;
    private String name;

    private Reservation(LocalDate date, LocalTime time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public static Reservation of(LocalDate date, LocalTime time, String name) {
        return new Reservation(date, time, name);
    }

    public boolean isDuplicate(Reservation other) {
        return this.getDate().isEqual(other.getDate()) && this.getTime().equals(other.getTime());
    }
}