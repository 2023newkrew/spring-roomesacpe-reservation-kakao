package nextstep.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nextstep.entity.Theme;

@Getter
@Builder
@AllArgsConstructor
public class ThemeResponseDto {

    private final Long id;

    private final String name;

    @JsonProperty("desc")
    private final String description;

    private final Integer price;

    public static ThemeResponseDto of(Theme theme) {
        return ThemeResponseDto.builder()
                .id(theme.getId())
                .name(theme.getName())
                .description(theme.getDescription())
                .price(theme.getPrice())
                .build();
    }
}
