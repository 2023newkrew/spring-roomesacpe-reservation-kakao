package roomescape.theme.dto;

public class ThemeDto {
    private String name;
    private String desc;
    private Integer price;

    public ThemeDto() {
    }

    public ThemeDto(String name, String desc, Integer price) {
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
