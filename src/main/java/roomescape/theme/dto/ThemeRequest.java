package roomescape.theme.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ThemeRequest {
    @NotEmpty(message = "테마 이름 필드가 비어있습니다.")
    private final String name;
    private final String desc;
    @Min(value = 0, message = "가격은 음수일 수 없습니다.")
    private final Integer price;

    public ThemeRequest(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getPrice() {
        return price;
    }
}
