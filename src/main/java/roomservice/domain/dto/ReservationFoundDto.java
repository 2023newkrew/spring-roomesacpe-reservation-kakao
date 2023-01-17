package roomservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * ReservationFoundDto contains what to show to clients when presenting reservation.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationFoundDto {
    @Positive
    private Long id; // DB에서 기본 키, auto_increment 되어있음
    @NotNull
    private LocalDate date; // 예약에서 date, time, theme이 모두 중복되는 경우 exception(중복 예약)
    @NotNull
    private LocalTime time;
    @NotBlank
    private String name;
    @NotNull
    private String themeName;
}
