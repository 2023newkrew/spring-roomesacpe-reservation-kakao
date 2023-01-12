package nextstep.domain.theme.dto;

public class ThemeRequestDto {
    private String name;
    private String desc;
    private int price;

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
