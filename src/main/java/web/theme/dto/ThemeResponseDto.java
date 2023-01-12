package web.theme.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import web.entity.Theme;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ThemeResponseDto {

    private Long id;
    private String name;
    private String desc;
    private int price;

    public static ThemeResponseDto of(Theme theme) {
        return new ThemeResponseDto(theme.getId(), theme.getName(), theme.getDesc(), theme.getPrice());
    }
}
