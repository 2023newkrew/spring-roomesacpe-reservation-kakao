package nextstep.reservation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.reservation.dto.ThemeRequestDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theme {
    private Long id;

    private String name;

    private String desc;

    private Integer price;

    public Theme(ThemeRequestDto themeRequestDto) {
        this.name = themeRequestDto.getName();
        this.desc = themeRequestDto.getDesc();
        this.price = themeRequestDto.getPrice();
    }
}
