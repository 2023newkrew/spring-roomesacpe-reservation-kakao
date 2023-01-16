package nextstep.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nextstep.theme.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
public class Reservation {

    @Getter
    @Setter
    private Long id;

    @Getter
    private final LocalDate date;

    @Getter
    private final LocalTime time;

    @Getter
    private final String name;

    @Getter
    private final Theme theme;
}
