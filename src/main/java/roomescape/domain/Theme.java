package roomescape.domain;


import static roomescape.utils.Messages.*;

public class Theme {
    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public Theme() {

    }

    public Theme(Long id, String name, String desc, Integer price) {
        checkEmptyName(name);
        this.id = id;
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

    public String createMessage(long themeId){
        return "Location: /themes/" + themeId;
    }

    public String toMessage(){
        return THEME_ID.getMessage() + id + ", " +
                THEME_NAME.getMessage() + name + ", " +
                THEME_DESC.getMessage() + desc + ", " +
                THEME_PRICE.getMessage() + price;
    }

    private void checkEmptyName(String name){
        if (name.length() == 0) {
            throw new NullPointerException(NAME_NOT_EMPTY_STRING.getMessage());
        }
    }
}
