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
    @NonNull
    private final Long id;
    @Nullable
    private final LocalDate date;
    @Nullable
    private final LocalTime time;
    @Nullable
    private final String name;
    @NonNull
    private final Long themeId;

}
