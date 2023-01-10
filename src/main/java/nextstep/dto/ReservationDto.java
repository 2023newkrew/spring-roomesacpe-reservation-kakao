package nextstep.dto;

import lombok.*;
import nextstep.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private String date;
    private String time;
    private String name;
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
