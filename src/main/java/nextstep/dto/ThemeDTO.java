package nextstep.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ThemeDTO {
    private Long id;

    private String name;

    private String desc;

    private Integer price;
}
