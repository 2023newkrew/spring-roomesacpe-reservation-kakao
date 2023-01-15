package nextstep.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationRequest {

    private String date;
    private String time;
    private String name;
    private String themeName;

    public CreateReservationRequest(String date, String time, String name, String themeName) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeName = themeName;
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

    public String getThemeName() {
        return themeName;
    }

    public LocalDate parseDate() {
        return LocalDate.parse(date);
    }

    public LocalTime parseTime() {
        return LocalTime.parse(time);
    }

}
