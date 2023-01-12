package nextstep.step1.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class ThemeDto {
    @Setter
    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public ThemeDto(String name, String desc, Integer price) {
        this(0L, name, desc, price);
    }

    public ThemeDto(Long id, String name, String desc, Integer price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }
}
