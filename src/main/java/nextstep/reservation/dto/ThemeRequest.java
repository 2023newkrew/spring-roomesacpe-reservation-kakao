package nextstep.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nextstep.reservation.entity.Theme;

@RequiredArgsConstructor
@Getter
@Builder
public class ThemeRequest {
    private final String name;
    private final String desc;
    private final Integer price;

    public Theme toEntityWithDummyId() {
        return Theme.builder()
                .id(null)
                .name(name)
                .desc(desc)
                .price(price)
                .build();
    }
}
