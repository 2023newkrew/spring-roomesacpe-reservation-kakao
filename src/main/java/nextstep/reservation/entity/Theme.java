package nextstep.reservation.entity;

import lombok.*;
import org.springframework.lang.Nullable;


@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
public class Theme {
    @Nullable
    private final Long id;
    @NonNull
    private final String name;
    @NonNull
    private final String desc;
    @NonNull
    private final Integer price;
}
