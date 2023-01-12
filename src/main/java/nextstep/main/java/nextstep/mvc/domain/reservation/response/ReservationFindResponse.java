package nextstep.main.java.nextstep.mvc.domain.reservation.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class ReservationFindResponse {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private String themeName;
    private String themeDesc;
    private int themePrice;
}
