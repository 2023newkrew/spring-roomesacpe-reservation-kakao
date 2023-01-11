package kakao.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateThemeRequest {
    @NotNull
    private final String name;

    @NotNull
    private final String desc;

    @NotNull
    @Min(0)
    private final Integer price;
}
