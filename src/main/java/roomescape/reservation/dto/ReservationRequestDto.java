package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.NoArgsConstructor;
import roomescape.entity.Reservation;
@NoArgsConstructor
public class ReservationRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    @JsonFormat
    private String name;
    @JsonFormat
    private Long themeId;

    public ReservationRequestDto(LocalDate date, LocalTime time, String name, Long themeId) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }

    @Override
    public String toString() {
        return "ReservationRequestDto{" +
                "date=" + date +
                ", time=" + time +
                ", name='" + name + '\'' +
                ", themeId=" + themeId +
                '}';
    }

    public Reservation toEntity() {
        return Reservation.builder().date(date).time(time).name(name).themeId(themeId).build();
    }
}
