package nextstep.main.java.nextstep.domain.theme;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThemeCreateRequestDto {
    private String name;
    private String desc;
    private int price;
}
