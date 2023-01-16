package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import roomescape.entity.Reservation;

@Builder
public class ReservationRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    @JsonFormat
    private String name;
    @JsonFormat
    private Long themeId;

    public Reservation toEntity() {
        return Reservation.builder().date(date).time(time).name(name).themeId(themeId).build();
    }
}
