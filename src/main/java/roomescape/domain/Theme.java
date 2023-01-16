package roomescape.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Theme {
    private final Long id;
    private final String name;
    private final String desc;
    private final Integer price;
}

