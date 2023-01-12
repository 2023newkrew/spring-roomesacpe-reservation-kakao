package nextstep.dto.web.request;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationRequest {
    private final String date;
    private final String time;
    private final String name;
    private final Long themeId;

    private CreateReservationRequest(String date, String time, String name, Long themeId) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }

    public static CreateReservationRequest of(String date, String time, String name) {
        return new CreateReservationRequest(date, time, name, Theme.DEFAULT_THEME_ID);
    }

    public static CreateReservationRequest of(String date, String time, String name, Long themeId) {
        return new CreateReservationRequest(date, time, name, themeId);
    }

    public Reservation toEntity() {
        return Reservation.of(
                LocalDate.parse(date),
                LocalTime.parse(time + ":00"),
                name,
                Theme.of(themeId, null, null, null)
        );
    }

    public LocalDate getDate() {
        return LocalDate.parse(date);
    }

    public LocalTime getTime() {
        return LocalTime.parse(time + ":00");
    }

    public String getName() {
        return name;
    }

    public Long getThemeId() {
        return themeId;
    }
}
