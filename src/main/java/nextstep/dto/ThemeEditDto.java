package nextstep.dto;

import lombok.Builder;
import lombok.Getter;
import nextstep.entity.Theme;

@Getter
public class ThemeEditDto extends ThemeBaseDto {
    private final Long id;

    @Builder
    public ThemeEditDto(Long id, String name, String description, Integer price) {
        super(name, description, price);
        this.id = id;
    }

    @Override
    public Theme toEntity() {
        Theme theme = super.toEntity();
        return Theme.createTheme(theme, id);
    }


}
