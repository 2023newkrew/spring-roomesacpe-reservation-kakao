package nextstep;

public class Theme {
    public static final Theme DEFAULT_THEME = Theme.of("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
    private final String name;
    private final String desc;
    private final Integer price;

    private Theme(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static Theme of(String name, String desc, Integer price) {
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
