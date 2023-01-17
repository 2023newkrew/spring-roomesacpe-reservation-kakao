package nextstep.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;

@Getter
@RequiredArgsConstructor
public class ReservationRequestDto {

    private final LocalDate date;

    private final LocalTime time;

    private final String name;

    @JsonProperty("theme_id")
    private final Long themeId;

    public Reservation toEntity(Theme theme){
        return Reservation.builder()
                .theme(theme)
                .date(this.date)
                .time(this.time)
                .name(this.name).build();
    }
}
