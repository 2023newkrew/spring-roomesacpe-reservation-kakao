package nextstep.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nextstep.reservation.entity.Theme;

@RequiredArgsConstructor
@Getter
@Builder
public class ThemeResponse {
    private final long id;
    private final String name;
    private final String desc;
    private final Integer price;

    public static ThemeResponse from(Theme theme) {
        return ThemeResponse.builder()
                .id(theme.getId())
                .name(theme.getName())
                .desc(theme.getDesc())
                .price(theme.getPrice())
                .build();
    }
}
