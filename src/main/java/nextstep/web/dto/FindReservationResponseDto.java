package nextstep.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindReservationResponseDto {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Theme theme;

    public static FindReservationResponseDto of(Reservation reservation) {
        return new FindReservationResponseDto(
                reservation.getId(),
                reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                reservation.getTheme()
        );
    }
}
