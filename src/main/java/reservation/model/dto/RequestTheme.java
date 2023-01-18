package reservation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestTheme {

    private String name;
    private String desc;
    private Integer price;

}
