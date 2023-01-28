package roomescape.theme.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.theme.entity.Theme;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ThemeResponseDTO {

    private final Long id;

    private final String name;

    private final String desc;

    private final int price;

    public static ThemeResponseDTO from(Theme theme) {
        return ThemeResponseDTO.builder()
                .id(theme.getId())
                .name(theme.getName())
                .desc(theme.getDesc())
                .price(theme.getPrice())
                .build();
    }
}
