package nextstep.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationRequest {

    private String date;
    private String time;
    private String name;

    public CreateReservationRequest(String date, String time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
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

    public LocalDate parseDate() {
        return LocalDate.parse(date);
    }

    public LocalTime parseTime() {
        return LocalTime.parse(time);
    }

}
