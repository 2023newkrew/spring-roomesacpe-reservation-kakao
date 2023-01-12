package nextstep.main.java.nextstep.mvc.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import nextstep.main.java.nextstep.mvc.domain.theme.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Theme theme;

}
