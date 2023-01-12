package kakao.dto.request;

public class CreateThemeRequest {
    public final String name;
    public final String desc;
    public final int price;

    public CreateThemeRequest(String name, String desc, int price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }
}
