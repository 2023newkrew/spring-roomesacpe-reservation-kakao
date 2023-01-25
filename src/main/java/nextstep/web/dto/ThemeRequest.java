package nextstep.web.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class ThemeRequest {

    @NotBlank
    @Length(max = 255)
    private final String name;

    @NotBlank
    @Length(max = 255)
    private final String desc;

    @Min(0)
    @Max(100_000)
    private final int price;

    public ThemeRequest(String name, String desc, int price) {
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

    public int getPrice() {
        return price;
    }
}
