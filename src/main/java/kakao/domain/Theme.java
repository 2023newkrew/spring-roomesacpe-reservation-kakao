package kakao.domain;

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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Theme)) return false;

        Theme cp = (Theme) obj;

        return this.name.equals(cp.name) && this.desc.equals(cp.desc) && this.price.equals(cp.price);
    }
}
