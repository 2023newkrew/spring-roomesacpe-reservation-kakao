package nextstep.web.dto;

public class ThemeResponse {

    private final long id;
    private final String name;
    private final String desc;
    private final int price;

    public ThemeResponse(long id, String name, String desc, int price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

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
}
