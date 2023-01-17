package nextstep.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ThemeCreateDto extends ThemeBaseDto{
    @Builder
    public ThemeCreateDto(String name, String description, Integer price) {
        super(name, description, price);
    }

}
