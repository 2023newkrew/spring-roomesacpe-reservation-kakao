package kakao.domain;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Theme {

    private Long id;
    private String name;
    private String desc;
    private Integer price;

    @Builder
    public Theme(Long id, String name, String desc, Integer price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void update(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Theme theme = (Theme) o;
        return Objects.equals(getId(), theme.getId()) && Objects.equals(getName(), theme.getName())
                && Objects.equals(getDesc(), theme.getDesc()) && Objects.equals(getPrice(),
                theme.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDesc(), getPrice());
    }
}
