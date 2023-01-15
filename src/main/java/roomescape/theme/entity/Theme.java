package roomescape.theme.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class Theme {

    private Long id;

    private final String name;

    private final String desc;

    private final Integer price;

    public void setId(final Long id) {
        this.id = id;
    }
}
