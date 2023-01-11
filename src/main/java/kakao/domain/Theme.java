package kakao.domain;

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
}
