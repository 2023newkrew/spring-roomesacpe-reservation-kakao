package roomescape.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class ReservationRequest {
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Long theme_id;
}