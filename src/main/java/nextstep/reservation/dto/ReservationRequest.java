package nextstep.reservation.dto;

import lombok.Data;

@Data
public class ReservationRequest {

    private String date;

    private String time;

    private String name;
}
