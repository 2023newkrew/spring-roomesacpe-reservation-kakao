package nextstep.domain.dto.theme;

public class CreateThemeDto {

    private final String name;
    private final String desc;
    private final Integer price;

    public CreateThemeDto(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getPrice() {
        return price;
    }
}
