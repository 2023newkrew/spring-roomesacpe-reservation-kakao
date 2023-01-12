package nextstep.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationResponseDTO {

    private final Long id;

    private final LocalDate date;

    private final LocalTime time;

    private final String name;

    private final String themeName;
    @JsonProperty("themeDesc")
    private final String themeDescription;

    private final Integer themePrice;
}
