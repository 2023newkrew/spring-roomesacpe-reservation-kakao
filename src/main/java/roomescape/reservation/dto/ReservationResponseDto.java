package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import roomescape.entity.Reservation;
import roomescape.entity.Theme;

@Getter
@Builder
public class ReservationResponseDto {
    private Long id;
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    private String name;
    private String themeName;
    private String themeDesc;
    private Integer themePrice;

    public static ReservationResponseDto of(Reservation reservation, Theme theme) {
        return ReservationResponseDto.builder()
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
