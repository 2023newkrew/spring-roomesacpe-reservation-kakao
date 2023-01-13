package nextstep.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Data
public class ReservationDTO {

    private final Long id;

    private final LocalDate date;

    private final LocalTime time;

    private final String name;

    private final ThemeDTO theme;
}
