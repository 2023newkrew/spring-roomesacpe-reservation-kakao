package nextstep.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateThemeRequest {
    @NotBlank(message = "테마 생성 시 이름을 기재해야 합니다.")
    String name;
    @NotBlank(message = "테마 생성 시 설명을 기재해야 합니다.")
    String desc;
    @NotNull(message = "테마 생성 시 가격을 기재해야 합니다.")
    Integer price;

    public CreateThemeRequest(String name, String desc, Integer price) {
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
