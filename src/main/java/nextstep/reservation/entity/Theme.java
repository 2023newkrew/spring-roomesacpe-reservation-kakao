package nextstep.reservation.entity;

import lombok.*;
import org.springframework.lang.Nullable;


@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
public class Theme {
    @NonNull
    private final Long id;
    @Nullable
    private final String name;
    @Nullable
    private final String desc;
    @Nullable
    private final Integer price;
}
