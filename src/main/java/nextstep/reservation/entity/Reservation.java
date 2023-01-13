package nextstep.reservation.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
public class Reservation {
    @Nullable
    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Long themeId;

}
