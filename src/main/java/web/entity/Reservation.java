package web.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reservation {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Long themeId;

    public static Reservation of(Long id, LocalDate date, LocalTime time, String name, Long themeId) {
        return new Reservation(id, date, time, name, themeId);
    }

    public boolean isDuplicate(Reservation other) {
        return this.getDate().isEqual(other.getDate()) && this.getTime().equals(other.getTime());
    }
}
