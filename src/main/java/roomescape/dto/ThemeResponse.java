package roomescape.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ThemeResponse {
    private Long id;
    private String name;
    private String desc;
    private Integer price;
}
