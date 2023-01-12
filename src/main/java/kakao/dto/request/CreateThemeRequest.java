package kakao.dto.request;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CreateThemeRequest {
    public String name;
    public String desc;
    public int price;

    public CreateThemeRequest(String name, String desc, int price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }
}
