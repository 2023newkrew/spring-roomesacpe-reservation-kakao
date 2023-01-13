package roomescape.domain;

import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Long theme_id;

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Long theme_id) {
        this.id = id;
        this.date = date;
        validateTime(time);
        this.time = time;
        this.name = name;
        this.theme_id = theme_id;
    }

    private void validateTime(LocalTime time) {
        if (!TimeTable.isExist(time)) {
            throw new RoomEscapeException(ErrorCode.TIME_TABLE_NOT_AVAILABLE);
        }
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

    public Long getTheme_id(){
        return theme_id;
    }
}
