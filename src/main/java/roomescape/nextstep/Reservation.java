package roomescape.nextstep;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final roomescape.nextstep.Theme theme;

    public Reservation(Long id, LocalDate date, LocalTime time, String name, roomescape.nextstep.Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    public Long getId() {
        return id;
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

    public roomescape.nextstep.Theme getTheme() {
        return theme;
    }
}
