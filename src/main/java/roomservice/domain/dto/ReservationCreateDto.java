package roomservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * ReservationCreateDto contains what to get from client when creating reservation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCreateDto {
    @NotNull
    @Positive
    private Long themeId;
    @NotNull
    private LocalDate date; // 예약에서 date, time, theme가 모두 중복되는 경우 exception(중복 예약)
    @NotNull
    private LocalTime time;
    @NotBlank
    private String name;
}
