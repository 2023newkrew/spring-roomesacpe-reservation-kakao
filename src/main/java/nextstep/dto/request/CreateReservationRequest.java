package nextstep.dto.request;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationRequest {
    private final String date;
    private final String time;
    private final String name;

    private CreateReservationRequest(String date, String time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public static CreateReservationRequest of(String date, String time, String name) {
        return new CreateReservationRequest(date, time, name);
    }

    public Reservation toEntity() {
        return Reservation.of(LocalDate.parse(date), LocalTime.parse(time + ":00"), name, Theme.DEFAULT_THEME);
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
