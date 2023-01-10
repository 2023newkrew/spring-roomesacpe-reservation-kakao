package nextstep.dto;

import lombok.*;
import nextstep.entity.Reservation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    @NonNull
    @Pattern(regexp = "{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])")
    private String date;

    @NonNull
    @Pattern(regexp = "(0[1-9]|1[0-9]|2[0-4]):(0[1-9]|[1-5][0-9])")
    private String time;

    @NotBlank
    private String name;

    @Setter
    private Long themeId;

    public LocalDate getLocalDate() {
        return LocalDate.parse(this.date, DateTimeFormatter.ISO_DATE);
    }

    public LocalTime getLocalTime() {
        return LocalTime.parse(this.time, DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public static Reservation from(ReservationDto reservationDto) {
        return new Reservation(reservationDto.getLocalDate(), reservationDto.getLocalTime()
                , reservationDto.getName(), reservationDto.getThemeId());
    }
}
