package reservation.dto.response;

import reservation.domain.Theme;

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

    public ThemeResponse(Theme theme) {
        this.id = theme.getId();
        this.name = theme.getName();
        this.desc = theme.getDesc();
        this.price = theme.getPrice();
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
