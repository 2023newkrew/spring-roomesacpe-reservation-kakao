package nextstep.dto;

import lombok.Data;

@Data
public class ReservationRequest {

    private String date;

    private String time;

    private String name;

    private Long theme_id;
}
