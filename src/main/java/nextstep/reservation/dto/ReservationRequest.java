package nextstep.reservation.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nextstep.reservation.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class ReservationRequest {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Long themeId;


    public Reservation toEntity() {
        return Reservation.builder()
                .date(date)
                .time(time)
                .name(name)
                .themeId(themeId)
                .build();
    }
}
