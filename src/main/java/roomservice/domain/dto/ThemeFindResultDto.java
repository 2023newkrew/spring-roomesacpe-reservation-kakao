package roomservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * ThemeFindResultDto contains what to show to clients when presenting information of theme.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeFindResultDto {
    @Positive
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    private String desc;
    @PositiveOrZero
    private Integer price;
}
