package nextstep.domain.theme;

import java.util.Objects;

public class Theme {

    private final Long id;
    private final String name;
    private final String desc;
    private final Integer price;

    public Theme(Long id, String name, String desc, Integer price) {
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

    @Override
    public boolean equals(Object obj) {
        Theme theme = (Theme) obj;
        return this.id.equals(theme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
