package nextstep.reservation.entity;

import lombok.*;
import nextstep.reservation.dto.ReservationRequestDto;

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
