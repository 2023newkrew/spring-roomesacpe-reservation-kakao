package web.entity;

public class Theme {
    private String name;
    private String desc;
    private int price;

    public Theme(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static Theme of(String name, String desc, int price) {
        return new Theme(name, desc, price);
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
