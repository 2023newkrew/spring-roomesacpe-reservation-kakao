package nextstep.domain;

import nextstep.exceptions.ErrorCode;
import nextstep.exceptions.exception.InvalidRequestException;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Theme theme;

    public Reservation(LocalDate date, LocalTime time, String name, Theme theme) {
        this(null, date, time, name, theme);
    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        validate(date, time, name);
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    private static void validate(LocalDate date, LocalTime time, String name) {
        if (date == null || time == null || name == null || name.length() == 0) {
            throw new InvalidRequestException(ErrorCode.INPUT_PARAMETER_INVALID);
        }
        if (!TimeTable.isValid(time)) {
            throw new InvalidRequestException(ErrorCode.TIME_INVALID);
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

    public Theme getTheme() {
        return theme;
    }

}
