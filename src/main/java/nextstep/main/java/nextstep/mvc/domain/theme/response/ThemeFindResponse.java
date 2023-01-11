package nextstep.main.java.nextstep.mvc.domain.theme.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ThemeFindResponse {
    private Long id;
    private String name;
    private String desc;
    private int price;
}
