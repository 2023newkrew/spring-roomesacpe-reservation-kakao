package nextstep.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    private String name;

    public Reservation toEntity() {
        return Reservation.builder()
                .date(date)
                .time(time)
                .name(name)
                .theme(Theme.builder()
                        .name("워너고홈")
                        .desc("병맛 어드벤처 회사 코믹물")
                        .price(29000)
                        .build())
                .build();
    }
}
