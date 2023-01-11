package roomescape.domain;

import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Theme theme;

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = id;
        this.date = date;
        validateTime(time);
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    private void validateTime(LocalTime time) {
        if (!TimeTable.isExist(time)) {
            throw new RoomEscapeException(ErrorCode.TIME_TABLE_NOT_AVAILABLE);
        }
    }

    public Reservation(LocalDate date, LocalTime time, String name, Theme theme) {
        this(null, date, time, name, theme);
    }

    public void setId(Long id) {
        this.id = id;
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

    public Theme getTheme() {
        return theme;
    }
}
