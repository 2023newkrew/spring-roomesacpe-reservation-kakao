package nextstep.roomescape.controller.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nextstep.roomescape.repository.model.Reservation;
import nextstep.roomescape.repository.model.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Builder
@Getter
public class ReservationResponseDTO {
    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final String themeName;
    private final String themeDesc;
    private final int themePrice;

    public static ReservationResponseDTO of(Reservation reservation) {
        Theme theme = reservation.getTheme();
        return ReservationResponseDTO.builder()
                .id(reservation.getId())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .name(reservation.getName())
                .themeName(theme.getName())
                .themeDesc(theme.getDesc())
                .themePrice(theme.getPrice())
                .build();
    }

}
