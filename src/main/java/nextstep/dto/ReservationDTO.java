package nextstep.dto;

import lombok.Data;
import nextstep.Theme;

import java.sql.Time;
import java.util.Date;

@Data
public class ReservationDTO {

    private Long id;

    private Date date;

    private Time time;

    private String name;

    private Theme theme;
}
