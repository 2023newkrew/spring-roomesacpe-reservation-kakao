package nextstep.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nextstep.dto.ThemeDto;

@ToString
@Getter
@EqualsAndHashCode
public class Theme {
    @Setter
    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public Theme(Long id, String name, String desc, Integer price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme(ThemeDto themeDto){
        this.id = themeDto.getId();
        this.name = themeDto.getName();
        this.desc = themeDto.getDesc();
        this.price = themeDto.getPrice();
    }
}
