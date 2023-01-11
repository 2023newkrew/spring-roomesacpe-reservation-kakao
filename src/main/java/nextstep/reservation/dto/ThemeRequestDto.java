package nextstep.reservation.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class ThemeRequestDto {
    private String name;
    private String desc;
    private Integer price;
}
