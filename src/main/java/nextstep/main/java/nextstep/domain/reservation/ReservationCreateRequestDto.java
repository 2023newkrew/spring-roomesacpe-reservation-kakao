package nextstep.main.java.nextstep.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class ReservationCreateRequestDto {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final String themeName;

}
