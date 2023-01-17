package web.dto;

import web.entity.Theme;

public class ThemeResponseDto {
    private long id;
    private String name;
    private String desc;
    private int price;

    public ThemeResponseDto(long id, String name, String desc, int price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static ThemeResponseDto of(long id, Theme theme) {
        return new ThemeResponseDto(id, theme.getName(), theme.getDesc(), theme.getPrice());
    }
}
