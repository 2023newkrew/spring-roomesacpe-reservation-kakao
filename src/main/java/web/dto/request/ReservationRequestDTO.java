package web.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import web.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class ReservationRequestDTO {

    private final String date;
    private final String time;
    private final String name;

    private final Long themeId;

    public Reservation toEntity() {
        return new Reservation(null, LocalDate.parse(date),
                LocalTime.parse(time + ":00"), name, themeId);
    }
}
