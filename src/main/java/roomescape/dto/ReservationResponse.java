package roomescape.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReservationResponse {
    private final Long id;
    private final String date;
    private final String time;
    private final String name;
    private final String themeName;
    private final String themeDesc;
    private final Integer themePrice;
}
