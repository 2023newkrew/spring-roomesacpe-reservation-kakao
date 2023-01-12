package roomservice.domain.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservationDto {
    private Long themeId;
    private LocalDate date; // 예약에서 date, time, theme이 모두 중복되는 경우 exception(중복 예약)
    private LocalTime time;
    private String name;
}
