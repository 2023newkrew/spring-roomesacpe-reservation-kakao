package nextstep.web.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nextstep.domain.Theme;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class ThemeResponseDto {

    private final Long id;

    private final String name;

    private final String desc;

    private final Integer price;

    public static ThemeResponseDto from(Theme theme) {
        return ThemeResponseDto.builder()
                .id(theme.getId())
                .name(theme.getName())
                .desc(theme.getDesc())
                .price(theme.getPrice())
                .build();
    }
}
