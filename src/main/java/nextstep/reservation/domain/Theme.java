package nextstep.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Theme {

    @Getter
    private final String name;

    @Getter
    private final String desc;

    @Getter
    private final Integer price;
}
