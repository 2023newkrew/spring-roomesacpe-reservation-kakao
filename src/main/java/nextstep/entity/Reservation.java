package nextstep.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class Reservation {

    private final Long id;

    private final LocalDate date;

    private final LocalTime time;

    private final String name;

    private final Theme theme;
}
