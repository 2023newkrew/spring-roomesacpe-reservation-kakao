package nextstep.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationRequest {
    private final String date;
    private final String time;
    private final String name;

    public CreateReservationRequest(String date, String time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
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
