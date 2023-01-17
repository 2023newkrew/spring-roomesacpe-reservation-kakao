package kakao.controller.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ThemeRequest {
    @NonNull
    private String name;

    @NonNull
    private String desc;

    @NonNull
    private Integer price;
}
