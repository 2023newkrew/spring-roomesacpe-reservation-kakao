package nextstep.reservation.entity;

import lombok.*;
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
    @NonNull
    private final LocalDate date;
    @NonNull
    private final LocalTime time;
    @NonNull
    private final String name;
    @NonNull
    private final Long themeId;

}
