package roomescape.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ThemeRequest {
    private String name;
    private String desc;
    private Integer price;
}
