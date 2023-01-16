package nextstep.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Data
public class ReservationDTO {

    private Long id;

    private LocalDate date;

    private LocalTime time;

    private String name;

    private Long theme_id;
}
