package nextstep.theme.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nextstep.theme.entity.Theme;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@EqualsAndHashCode
public class ThemeDto {
    @Setter
    private Long id;
    @NotBlank
    private String name;
    private String desc;
    @NotNull
    private Integer price;

    public ThemeDto(Long id, String name, String desc, Integer price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme toEntity(){
        return new Theme(this.id, this.name, this.desc, this.price);
    }
}
