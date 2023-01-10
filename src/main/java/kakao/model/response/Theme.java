package kakao.model.response;

import lombok.Getter;

@Getter
public class Theme {
    private final String name;
    private final String desc;
    private final Integer price;

    public Theme(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }
}
