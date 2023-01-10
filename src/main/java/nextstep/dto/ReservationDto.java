package nextstep.dto;

import lombok.*;

@Getter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private String date;
    private String time;
    private String name;
    private Long themeId;
}
