package nextstep.dto;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.exception.InvalidRequestException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class ReservationCreateRequest {
    private final String date;
    private final String time;
    private final String name;
    private final Long themeId;

    public ReservationCreateRequest(String date, String time, String name, Long themeId) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }

    public Reservation toReservation(Theme theme) {
        validateInput(date, time, name, themeId);
        try {
            LocalDate localDate = LocalDate.parse(date);
            LocalTime localTime = LocalTime.parse(time + ":00");

            return new Reservation(localDate, localTime, name, theme);
        } catch (DateTimeParseException e) {
            throw new InvalidRequestException();
        }
    }

    private static void validateInput(String date, String time, String name, Long themeId) {
        if (!StringUtils.hasText(date) || !StringUtils.hasText(time) || !StringUtils.hasText(name) || ObjectUtils.isEmpty(themeId)) {
            throw new InvalidRequestException();
        }
    }

    public Long getThemeId() {
        return themeId;
    }
}
