package nextstep.reservations.dto.theme;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ThemeResponseDto {
    private Long id;

    private String name;

    private String desc;

    private Integer price;
}
