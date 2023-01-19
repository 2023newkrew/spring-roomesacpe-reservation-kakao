package nextstep.domain;

public class Theme {
    private final Long id;
    private String name;
    private String desc;
    private Integer price;

    public Theme(String name, String desc, Integer price) {

        this.id = null;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme(Long id, String name, String desc, Integer price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme(long theme_id) {
        this.id = theme_id;
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
