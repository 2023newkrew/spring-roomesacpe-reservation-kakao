package nextstep.reservation.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;


@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
public class Theme {
    @Nullable
    private final Long id;
    private final String name;
    private final String desc;
    private final Integer price;
}
