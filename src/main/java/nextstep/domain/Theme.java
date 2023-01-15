package nextstep.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Theme {
    private Long id;
    private String name;
    private String desc;
    private Integer price;
}
