package kakao.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Reservation implements Comparable<Reservation> {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Theme theme;

    public Reservation(LocalDate date, LocalTime time, String name, Theme theme) {
        this(null, date, time, name, theme);
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int compareTo(Reservation reservation) {
        return Long.compare(this.id, reservation.id);
    }

    public boolean isDuplicate(LocalDate date, LocalTime time) {
        if (this.date.equals(date) && this.time.equals(time)) {
            return true;
        }
        return false;
    }
}
