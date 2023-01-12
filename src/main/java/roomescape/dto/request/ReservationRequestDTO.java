package roomescape.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;

@Getter
@RequiredArgsConstructor
public class ReservationRequestDTO {

    @NotNull
    private final String date;

    @NotNull
    private final String time;

    @NotNull
    private final String name;

    public Reservation toEntity(Theme theme) {
        return new Reservation(null, LocalDate.parse(date),
                LocalTime.parse(time + ":00"), this.name, theme);
    }
}