package roomescape.controller.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Theme;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class ReservationRequest {

    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Long themeId;

    public ReservationRequest(String date, String time, String name, String themeId) {
        this.date = validateDate(date);
        this.time = validateTime(time);
        this.name = name;
        this.themeId = validateThemeId(themeId);
    }


    private LocalDate validateDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new RoomEscapeException(ErrorCode.VALID_INPUT_REQUIRED);
        }
    }

    private LocalTime validateTime(String time) {
        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new RoomEscapeException(ErrorCode.VALID_INPUT_REQUIRED);
        }
    }

    private Long validateThemeId(String themeId) {
        try {
            return Long.valueOf(themeId);
        } catch (NumberFormatException e) {
            throw new RoomEscapeException(ErrorCode.VALID_INPUT_REQUIRED);
        }
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

    public Long getThemeId() {
        return themeId;
    }

    public Reservation toEntity(Theme theme) {
        return new Reservation(
                date,
                time,
                name,
                theme
        );
    }
}