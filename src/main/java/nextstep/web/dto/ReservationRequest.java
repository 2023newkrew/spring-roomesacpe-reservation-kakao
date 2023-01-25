package nextstep.web.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class ReservationRequest {

    @NotBlank
    @Length(max = 255)
    private final String name;
    private final LocalDate date;
    private final LocalTime time;
    private final Long themeId;

    public ReservationRequest(String name, LocalDate date, LocalTime time, Long themeId) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.themeId = themeId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Long getThemeId() {
        return themeId;
    }
}
