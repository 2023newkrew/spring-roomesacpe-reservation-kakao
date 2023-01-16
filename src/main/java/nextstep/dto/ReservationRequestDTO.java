package nextstep.dto;

import lombok.Data;

@Data
public class ReservationRequestDTO {

    private final String date;

    private final String time;

    private final String name;

    private final Long theme_id;
}
