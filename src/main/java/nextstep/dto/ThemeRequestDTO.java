package nextstep.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class ThemeRequestDTO {
    private String name;

    private String desc;

    private int price;
}
