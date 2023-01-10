package nextstep.reservation.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ThemeRequestDto {
    private String name;
    private String desc;
    private Integer price;
}
