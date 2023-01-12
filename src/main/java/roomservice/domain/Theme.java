package roomservice.domain;

import lombok.Data;

/**
 * Theme class is DTO class, containing room-escape theme information such as name, description, price.
 */
@Data
public class Theme {
    private String name;
    private String desc;
    private Integer price;

    public Theme(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }
}
