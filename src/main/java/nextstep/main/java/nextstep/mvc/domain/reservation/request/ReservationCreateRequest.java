package nextstep.main.java.nextstep.mvc.domain.reservation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ReservationCreateRequest {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final String themeName;

}
