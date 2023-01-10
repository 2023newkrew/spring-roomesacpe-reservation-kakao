package nextstep.reservation.entity;

import lombok.*;
import nextstep.reservation.dto.ReservationRequestDto;

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

    public Reservation(ReservationRequestDto reservationRequestDto) {
        this.date = reservationRequestDto.getDate();
        this.time = reservationRequestDto.getTime();
        this.name = reservationRequestDto.getName();
        this.theme = new Theme("워너고홈 ", "병맛 어드벤처 회사 코믹물", 29000);
    }
}
