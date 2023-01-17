package web.entity;

public class Theme {
    private final long id;
    private final String name;
    private final String desc;
    private final int price;

    public Theme(long id, String name, String desc, Integer price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static Theme of(long id, String name, String desc, int price) {
        return new Theme(id, name, desc, price);
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

    public long getId() {
        return id;
    }
}
