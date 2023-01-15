package nextstep.dto;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

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
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time + ":00");

        return new Reservation(
                localDate,
                localTime,
                name,
                theme
        );
    }

    public Long getThemeId() {
        return themeId;
    }
}
