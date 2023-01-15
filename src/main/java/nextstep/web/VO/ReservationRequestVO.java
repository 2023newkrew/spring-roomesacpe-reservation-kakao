package nextstep.web.VO;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationRequestVO {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final String themeName;
}
