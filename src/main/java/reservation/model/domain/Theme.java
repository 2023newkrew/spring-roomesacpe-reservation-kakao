package reservation.model.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Theme {
    private Long id;
    private String name;
    private String desc;
    private Integer price;
}