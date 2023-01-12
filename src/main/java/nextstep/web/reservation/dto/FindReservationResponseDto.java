package nextstep.web.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.domain.Reservation;

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
    private Long themeId;

    public static FindReservationResponseDto of(Reservation reservation) {
        return new FindReservationResponseDto(
                reservation.getId(),
                reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                reservation.getThemeId()
        );
    }
}
