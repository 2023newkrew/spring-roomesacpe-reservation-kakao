package nextstep.entity;

import lombok.Getter;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@ToString
@Getter
public class Reservation {
    private Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Long themeId;

    public Reservation(LocalDate date, LocalTime time, String name, Long themeId) {
        this(0L, date, time, name, themeId);
    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Long themeId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setId(Long id) {
        this.id = id;
    }
}
