package nextstep.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Theme {

    @Getter
    private String name;

    @Getter
    private String desc;
    
    @Getter
    private Integer price;
}
