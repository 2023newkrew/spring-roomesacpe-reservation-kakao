package nextstep.roomescape.controller.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.roomescape.repository.model.Reservation;
import nextstep.roomescape.repository.model.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ReservationRequestDTO {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Theme theme;

    public Reservation toEntity() {
        return Reservation.builder()
                .date(date)
                .time(time)
                .name(name)
                .theme(theme)
                .build();
    }

}
