package web.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReservationResponseDTO {

    private final Long id;
    private final String date;
    private final String time;
    private final String name;
    private final String themeName;
    private final String themeDesc;
    private final Integer themePrice;
}
