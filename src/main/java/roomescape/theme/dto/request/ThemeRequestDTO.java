package roomescape.theme.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.theme.entity.Theme;

@Getter
@Builder
@RequiredArgsConstructor
public class ThemeRequestDTO {

    @NotNull
    private final String name;

    @NotNull
    private final String desc;

    @NotNull
    private final int price;

    public Theme toEntity() {
        return Theme.builder()
                .name(this.name)
                .desc(this.desc)
                .price(this.price)
                .build();
    }
}
