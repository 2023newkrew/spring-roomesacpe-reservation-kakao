package nextstep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ReservationResponseDTO {

    private final Long id;

    private final LocalDate date;

    private final LocalTime time;

    private final String name;

    private final String themeName;

    private final String themeDesc;

    private final Integer themePrice;
}
