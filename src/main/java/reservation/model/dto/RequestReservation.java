package reservation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class RequestReservation {
    private LocalDate date;
    private LocalTime time;
    private String username;
    private Long themeId;
}
