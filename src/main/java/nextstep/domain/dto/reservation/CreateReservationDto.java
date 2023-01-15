package nextstep.domain.dto.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationDto {

    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Long themeId;

    public CreateReservationDto(LocalDate date, LocalTime time, String name, Long themeId) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }

    public static CreateReservationDto create(String date, String time, String name, Long themeId) {
        return new CreateReservationDto(
            LocalDate.parse(date),
            LocalTime.parse(time),
            name,
            themeId
        );
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Long getThemeId() {
        return themeId;
    }

}
