package web.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReservationRequestDTO {

    private final String date;
    private final String time;
    private final String name;
}
