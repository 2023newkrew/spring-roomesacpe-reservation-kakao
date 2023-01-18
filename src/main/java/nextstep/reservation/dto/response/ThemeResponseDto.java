package nextstep.reservation.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nextstep.reservation.entity.Theme;

@Getter
@Setter
@ToString
public class ThemeResponseDto {
    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public ThemeResponseDto (Theme theme) {
        this.id = theme.getId();
        this.name = theme.getName();
        this.desc = theme.getDesc();
        this.price = theme.getPrice();
    }
}
