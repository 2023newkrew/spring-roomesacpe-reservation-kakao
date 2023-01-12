package nextstep.dto.console.request;

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
