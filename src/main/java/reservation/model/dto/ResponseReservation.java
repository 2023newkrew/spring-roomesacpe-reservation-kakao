package reservation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import reservation.model.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ResponseReservation {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Theme theme;
}
