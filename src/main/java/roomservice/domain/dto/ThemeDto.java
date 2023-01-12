package roomservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class ThemeDto {
    @NotBlank
    private String name;
    private String desc;
    @Positive
    private Integer price;
}
