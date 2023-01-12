package web.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Theme {

    private String name;
    private String desc;
    private int price;

    public static Theme of(String name, String desc, int price) {
        return new Theme(name, desc, price);
    }
}
