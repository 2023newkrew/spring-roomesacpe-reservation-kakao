package kakao.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateThemeRequest {
    @NotNull
    public final String name;

    @NotNull
    public final String desc;

    @NotNull
    @Min(0)
    public final Integer price;
}
