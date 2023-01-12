package nextstep.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateThemeRequestDto {

    @NotNull
    @NotBlank
    private String name;

    private String desc;

    @Min(1)
    private Integer price;
}
