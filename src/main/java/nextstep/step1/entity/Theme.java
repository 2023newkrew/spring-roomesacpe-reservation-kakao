package nextstep.step1.entity;

import lombok.Getter;
import lombok.ToString;
import nextstep.step1.dto.ThemeDto;

@ToString
@Getter
public class Theme {
    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public Theme(String name, String desc, Integer price) {
        this(0L, name, desc, price);
    }

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
