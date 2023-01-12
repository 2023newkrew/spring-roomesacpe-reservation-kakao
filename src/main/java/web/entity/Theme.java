package web.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Theme {

    private Long id;
    private String name;
    private String desc;
    private int price;

    public static Theme of(Long id, String name, String desc, int price) {
        return new Theme(id, name, desc, price);
    }
}
