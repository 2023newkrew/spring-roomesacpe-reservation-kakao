package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationCreateRequest {

    private String date;
    private String time;
    private String name;
    private Long themeId;

    public ReservationCreateRequest() {}

    public ReservationCreateRequest(String date, String time, String name, Long themeId) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }

    public Reservation toReservation(Theme theme) {
        return new Reservation(
                null,
                LocalDate.parse(date, DateTimeFormatter.ISO_DATE),
                LocalTime.parse(time),
                name,
                theme

        );
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Long getThemeId() {
        return themeId;
    }

}
