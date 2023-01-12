package nextstep.web.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationRequestDto {

    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    @NotBlank(message = "이름을 확인해 주세요.")
    private String name;

    @NotNull(message = "테마 ID를 확인해 주세요")
    @Min(value = 1, message = "테마 ID를 확인해 주세요")
    private Long themeId;
}
