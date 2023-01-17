package nextstep.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nextstep.entity.Reservation;

@Getter
@AllArgsConstructor
@Builder
public class ReservationResponseDto {

    private final Long id;

    private final LocalDate date;

    private final LocalTime time;

    private final String name;

    private final String themeName;
    @JsonProperty("themeDesc")
    private final String themeDescription;

    private final Integer themePrice;

    public static ReservationResponseDto of(Reservation reservation){
        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .name(reservation.getName())
                .themeName(reservation.getTheme().getName())
                .themeDescription(reservation.getTheme().getDescription())
                .themePrice(reservation.getTheme().getPrice())
                .build();
    }



}
