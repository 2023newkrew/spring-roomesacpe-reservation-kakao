package web.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import web.entity.Theme;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ThemeRequestDto {

    @Length(min = 1, max = 20)
    @NotEmpty
    @NotBlank
    private String name;
    @Length(min = 1, max = 40)
    @NotEmpty
    @NotBlank
    private String desc;
    @NotNull
    @Range(min = 0)
    private int price;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getPrice() {
        return price;
    }

    public Theme toEntity() {
        return Theme.of(0, name, desc, price);
    }
}
