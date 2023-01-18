package nextstep.roomescape.repository.model;

import lombok.*;
import org.springframework.lang.Nullable;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Theme {

    @Nullable
    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public Theme(String name, String desc, Integer price) {
        this(null, name, desc, price);
    }

}
