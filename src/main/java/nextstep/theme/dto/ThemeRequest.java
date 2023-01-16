package nextstep.theme.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Data
@Setter(AccessLevel.NONE)
public class ThemeRequest {

    private final String name;

    private final String desc;

    private final Integer price;

    public ThemeRequest() {
        this(null, null, null);
    }
}
