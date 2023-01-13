package nextstep.main.java.nextstep.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ReservationCreateRequestDto {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
}
