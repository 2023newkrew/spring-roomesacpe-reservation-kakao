package nextstep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Getter
public class ReservationDTO {

    private final Long id;

    private final LocalDate date;

    private final LocalTime time;

    private final String name;

    private final Long theme_id;
}
