package nextstep.dto;

import lombok.*;
import nextstep.entity.Reservation;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class ReservationDto {
    @NonNull
    private LocalDate date;

    @NonNull
    private LocalTime time;

    @NotBlank
    private String name;

    @Setter
    private Long themeId;

    public static Reservation from(ReservationDto reservationDto) {
        return new Reservation(reservationDto.getDate(), reservationDto.getTime()
                , reservationDto.getName(), reservationDto.getThemeId());
    }
}
