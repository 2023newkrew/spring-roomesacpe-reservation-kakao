package nextstep.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Getter
    @Setter
    private Long id;

    @Getter
    private LocalDate date;

    @Getter
    private LocalTime time;

    @Getter
    private String name;

    @Getter
    private Theme theme;
}
