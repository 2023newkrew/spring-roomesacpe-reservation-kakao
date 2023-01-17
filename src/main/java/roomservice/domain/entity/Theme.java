package roomservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Theme class is Entity class, containing room-escape theme information such as name, description, price.
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Theme {
    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public void setId(Long id) {
        this.id = id;
    }
}
