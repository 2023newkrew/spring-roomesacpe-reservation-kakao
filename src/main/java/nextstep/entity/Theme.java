package nextstep.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Theme {

    private final String name;

    private final String description;

    private final Integer price;
}
