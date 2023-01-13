package roomescape.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.domain.Reservation;

@Getter
@RequiredArgsConstructor
public class ReservationRequestDTO {

    @NotNull
    private final String date;

    @NotNull
    private final String time;

    @NotNull
    private final String name;

    public Reservation toEntity(Long themeId) {
        return Reservation.builder()
                .date(LocalDate.parse(this.date))
                .time(LocalTime.parse(this.time + ":00"))
                .name(this.name)
                .themeId(themeId)
                .build();
    }
}
