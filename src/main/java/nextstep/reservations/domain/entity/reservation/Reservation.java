package nextstep.reservations.domain.entity.reservation;

import lombok.*;
import nextstep.reservations.domain.entity.theme.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class Reservation {
    private Long id;

    private LocalDate date;

    private LocalTime time;

    private String name;

    private Theme theme;
}
