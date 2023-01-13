package nextstep.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Reservation {

    private final Long id;

    private final LocalDate date;

    private final LocalTime time;

    private final String name;

    private final Theme theme;
}
