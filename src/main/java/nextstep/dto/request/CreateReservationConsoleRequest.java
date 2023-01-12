package nextstep.dto.request;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationConsoleRequest {
    private final String date;
    private final String time;
    private final String name;

    private CreateReservationConsoleRequest(String date, String time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public static CreateReservationConsoleRequest of(String date, String time, String name) {
        return new CreateReservationConsoleRequest(date, time, name);
    }

    public Reservation toEntity() {
        return Reservation.of(
                LocalDate.parse(date),
                LocalTime.parse(time + ":00"),
                name,
                Theme.DEFAULT_THEME
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
}
