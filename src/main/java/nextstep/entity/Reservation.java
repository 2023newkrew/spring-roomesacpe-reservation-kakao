package nextstep.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalTime;

@ToString
@Getter
@EqualsAndHashCode
public class Reservation {
    @Setter
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
}
