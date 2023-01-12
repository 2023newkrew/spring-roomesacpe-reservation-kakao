package nextstep.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationRequest {

    @Pattern(
            regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
            message = "일자가 형식에 맞지 않습니다."
    )
    private String date;

    @Pattern(
            regexp = "^(0[0-9]|1[0-9]|2[0-3]):(0[1-9]|[0-5][0-9])$",
            message = "시각이 형식에 맞지 않습니다."
    )
    private String time;

    @NotBlank
    private String name;
}
