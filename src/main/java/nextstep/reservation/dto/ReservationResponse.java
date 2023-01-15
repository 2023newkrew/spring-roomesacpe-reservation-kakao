package nextstep.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nextstep.reservation.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
@Builder
@Getter
public class ReservationResponse {
    private final long id;
    private final LocalDate date;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "kk:mm")
    private final LocalTime time;
    private final String name;
    private final String themeName;
    private final String themeDesc;
    private final int themePrice;

    public static ReservationResponse from(Reservation reservation, ThemeResponse theme) {
        return ReservationResponse.builder()
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
