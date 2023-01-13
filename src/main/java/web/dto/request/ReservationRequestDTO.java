package web.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import web.domain.Reservation;
import web.domain.Theme;

@Getter
@RequiredArgsConstructor
public class ReservationRequestDTO {

    private final String date;
    private final String time;
    private final String name;

    public Reservation toEntity(Theme theme) {
        return new Reservation(null, LocalDate.parse(date),
                LocalTime.parse(time + ":00"), this.name, theme);
    }
}
