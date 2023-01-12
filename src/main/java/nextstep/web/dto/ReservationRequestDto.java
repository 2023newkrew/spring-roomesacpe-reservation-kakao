package nextstep.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
@Getter
public class ReservationRequestDto {

    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private final LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")
    private final LocalTime time;

    private final String name;
}
