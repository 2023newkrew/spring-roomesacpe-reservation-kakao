package domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class Reservation implements Comparable<Reservation> {

    private Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Theme theme;

    public String getThemeName() {
        return theme.getName();
    }

    public String getThemeDesc() {
        return theme.getDesc();
    }

    public int getThemePrice() {
        return theme.getPrice();
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int compareTo(Reservation reservation) {
        return Long.compare(this.id, reservation.id);
    }

    public boolean isDuplicate(LocalDate date, LocalTime time) {
        return this.date.equals(date) && this.time.equals(time);
    }
}
