package nextstep.web.theme.dto;

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

    @NotNull(message = "테마명을 확인해 주세요")
    @NotBlank(message = "테마명을 확인해 주세요")
    private String name;

    private String desc;

    @Min(value = 1, message = "가격을 확인해 주세요.")
    private Integer price;
}
