package nextstep.dto;

public class ThemeResponse {

    private final long id;
    private String name;
    private String desc;
    private Integer price;

    public ThemeResponse(long id, String name, String desc, Integer price) {
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

    public Integer getPrice() {
        return price;
    }
}
