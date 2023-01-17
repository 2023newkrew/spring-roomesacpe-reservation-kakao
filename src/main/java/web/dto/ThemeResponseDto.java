package web.dto;

import web.entity.Theme;

public class ThemeResponseDto {
    private long id;
    private String name;
    private String desc;

    public long getId() {
        return id;
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

    private int price;

    public ThemeResponseDto(long id, String name, String desc, int price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static ThemeResponseDto of(Theme theme) {
        return new ThemeResponseDto(theme.getId(), theme.getName(), theme.getDesc(), theme.getPrice());
    }
}
