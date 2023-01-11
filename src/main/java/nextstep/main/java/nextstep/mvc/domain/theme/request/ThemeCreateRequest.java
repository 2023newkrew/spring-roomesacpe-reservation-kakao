package nextstep.main.java.nextstep.mvc.domain.theme.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThemeCreateRequest {
    private String name;
    private String desc;
    private int price;
}
