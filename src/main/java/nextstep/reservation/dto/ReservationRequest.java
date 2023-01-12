package nextstep.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReservationRequest {

    private String date;

    private String time;

    private String name;
}
