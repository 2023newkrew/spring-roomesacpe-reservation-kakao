package nextstep.reservation.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;

    private Theme theme;

}
