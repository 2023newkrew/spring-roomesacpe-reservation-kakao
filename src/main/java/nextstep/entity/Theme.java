package nextstep.entity;

import nextstep.dto.ThemeDto;

public class Theme {
    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public Theme(String name, String desc, Integer price) {
        this(0L, name, desc, price);
    }

    public Theme(Long id, String name, String desc, Integer price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme(ThemeDto themeDto){
        this.name = themeDto.getName();
        this.desc = themeDto.getDesc();
        this.price = themeDto.getPrice();
    }

    public Long getId() {
        return id;
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
