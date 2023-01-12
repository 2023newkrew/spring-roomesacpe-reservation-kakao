package roomescape.dto;

public class ThemeRequestDto {
    private String name;
    private String desc;
    private Integer price;

    public ThemeRequestDto() {}

    public ThemeRequestDto(String name, String desc, Integer price) {
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
