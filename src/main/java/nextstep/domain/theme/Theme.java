package nextstep.domain.theme;

public class Theme {
    private String name;
    private String desc;
    private Integer price;

    public Theme(String name, String desc, Integer price) {
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

    public boolean equals(Theme theme) {
        return this.name.equals(theme.getName())
                && this.desc.equals(theme.getDesc())
                && this.price.equals(theme.getPrice());
    }
}
