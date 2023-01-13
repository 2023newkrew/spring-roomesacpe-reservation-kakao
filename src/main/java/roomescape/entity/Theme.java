package roomescape.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Theme {
    private Long themeId;
    private String name;
    private String desc;
    private Integer price;

    public Theme(Long themeId, String name, String desc, Integer price) {
        this.themeId = themeId;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }
}
