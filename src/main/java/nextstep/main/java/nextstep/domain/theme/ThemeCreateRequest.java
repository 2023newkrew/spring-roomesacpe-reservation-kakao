package nextstep.main.java.nextstep.domain.theme;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThemeCreateRequest {
    private String name;
    private String desc;
    private int price;
}
