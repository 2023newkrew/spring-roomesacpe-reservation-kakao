package nextstep.domain.theme.dto;

public class ThemeRequestDto {
    private final String name;
    private final String desc;
    private final int price;

    public ThemeRequestDto(String name, String desc, int price) {
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

    public int getPrice() {
        return price;
    }
}
