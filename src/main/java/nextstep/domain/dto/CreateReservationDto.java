package nextstep.domain.dto;

import java.sql.Time;
import java.sql.Date;

public class CreateReservationDto {

    private final String date;
    private final String time;
    private final String name;
    private final Long themeId;

    public CreateReservationDto(String date, String time, String name, Long themeId) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
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
