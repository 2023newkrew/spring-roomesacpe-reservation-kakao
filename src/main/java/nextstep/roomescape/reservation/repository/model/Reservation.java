package nextstep.roomescape.reservation.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.roomescape.theme.repository.model.Theme;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Nullable
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Theme theme;
}
