package roomescape.dto;

import nextstep.Reservation;
import nextstep.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationRequest {

    private String date;
    private String time;
    private String name;

    public ReservationRequest() {}

    public ReservationRequest(String date, String time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public Reservation toReservation() {
        return new Reservation(
                null,
                LocalDate.parse(date, DateTimeFormatter.ISO_DATE),
                LocalTime.parse(time),
                name,
                new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000)
        );
    }

    public Reservation toReservation(Long id) {
        return new Reservation(
                id,
                LocalDate.parse(date, DateTimeFormatter.ISO_DATE),
                LocalTime.parse(time),
                name,
                new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000)
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

}
