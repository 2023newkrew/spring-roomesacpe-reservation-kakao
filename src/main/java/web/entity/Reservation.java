package web.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reservation {

    private LocalDate date;
    private LocalTime time;
    private String name;

    public static Reservation of(LocalDate date, LocalTime time, String name) {
        return new Reservation(date, time, name);
    }

    public boolean isDuplicate(Reservation other) {
        return this.getDate().isEqual(other.getDate()) && this.getTime().equals(other.getTime());
    }
}
