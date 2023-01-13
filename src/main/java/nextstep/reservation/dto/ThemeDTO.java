package nextstep.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ThemeDTO {

    private final String name;

    private final String desc;

    private final Integer price;
}
