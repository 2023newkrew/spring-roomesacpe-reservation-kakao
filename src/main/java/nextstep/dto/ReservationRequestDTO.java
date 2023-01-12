package nextstep.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class ReservationRequestDTO {

    private final LocalDate date;

    private final LocalTime time;

    private final String name;
}
