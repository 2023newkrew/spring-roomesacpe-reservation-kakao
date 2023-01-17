package web.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final long themeId;

    private Reservation(LocalDate date, LocalTime time, String name, long themeId) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }

    public static Reservation of(LocalDate date, LocalTime time, String name, long themeId) {
        return new Reservation(date, time, name, themeId);
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

    public long getThemeId() {
        return themeId;
    }

    public boolean isDuplicate(Reservation other) {
        return this.getDate().isEqual(other.getDate()) && this.getTime().equals(other.getTime());
    }
}
