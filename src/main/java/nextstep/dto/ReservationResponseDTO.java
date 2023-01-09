package nextstep.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.domain.Theme;

public class ReservationResponseDTO {

    private final Long id;

    private final LocalDate date;

    private final LocalTime time;

    private final String name;

    @JsonUnwrapped(prefix = "theme")
    private final Theme theme;

//    private final String themeName;
//    private final String themeDesc;
//    private final String theme;


    public ReservationResponseDTO(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    public Long getId() {
        return id;
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

    public Theme getTheme() {
        return theme;
    }
}
