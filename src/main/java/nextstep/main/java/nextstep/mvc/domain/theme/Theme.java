package nextstep.main.java.nextstep.mvc.domain.theme;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Theme {
    private Long id;
    private String name;
    private String desc;
    private Integer price;
}
