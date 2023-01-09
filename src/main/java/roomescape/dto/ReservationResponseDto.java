package roomescape.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nextstep.Reservation;
import nextstep.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class ReservationResponseDto {

    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Theme theme;

    public static ReservationResponseDto of(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                reservation.getTheme()
        );
    }
}
