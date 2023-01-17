package nextstep.roomescape.theme.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.roomescape.theme.repository.model.Theme;

@Getter
@AllArgsConstructor
public class ThemeRequestDTO {
    private String name;
    private String desc;
    private int price;

    public Theme toEntity() {
        return new Theme(
                this.name,
                this.desc,
                this.price
        );
    }
}
