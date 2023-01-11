package nextstep.reservation.entity;

import lombok.*;
import nextstep.reservation.dto.ThemeRequestDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class Theme {
    private Long id;

    private String name;

    private String desc;

    private Integer price;
}
