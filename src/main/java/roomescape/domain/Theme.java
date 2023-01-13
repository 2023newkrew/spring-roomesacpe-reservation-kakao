package roomescape.domain;


public class Theme {
    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public Theme() {

    }

    public Theme(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
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

    public String toMessage(){
        return "Name: " + name + ", Desc: " + desc + ", Price: " + price;
    }
}
