package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationRequest {

    private String date;
    private String time;
    private String name;
    private Long theme_id;

    public ReservationRequest() {}

    public ReservationRequest(String date, String time, String name, Long theme_id) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme_id = theme_id;
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

    public Reservation toReservation(Theme theme, Long id) {
        return new Reservation(
                id,
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

    public Long getTheme_id() {
        return theme_id;
    }
}
