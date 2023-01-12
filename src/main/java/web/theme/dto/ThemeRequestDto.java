package web.theme.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import web.entity.Theme;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
public class ThemeRequestDto {

    @Length(min = 1, max = 20)
    @NotBlank
    private String name;
    @Length(min = 1, max = 255)
    private String desc;
    @Min(0)
    @Max(1_000_000)
    private int price;

    public Theme toEntity() {
        return Theme.of(null, name, desc, price);
    }
}
