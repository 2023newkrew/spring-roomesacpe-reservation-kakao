package nextstep.reservation.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nextstep.reservation.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import static nextstep.reservation.constant.RoomEscapeConstant.DUMMY_ID;

@Getter
@RequiredArgsConstructor
@Builder
public class ReservationRequest {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Long themeId;


    public Reservation toEntityWithDummyId() {
        return Reservation.builder()
                .id(DUMMY_ID)
                .date(date)
                .time(time)
                .name(name)
                .themeId(themeId)
                .build();
    }
}
