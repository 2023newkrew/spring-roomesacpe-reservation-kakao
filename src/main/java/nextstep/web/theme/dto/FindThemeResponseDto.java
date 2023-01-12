package nextstep.web.theme.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.domain.Theme;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindThemeResponseDto {

    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public static FindThemeResponseDto of(Theme theme) {
        return new FindThemeResponseDto(
                theme.getId(),
                theme.getName(),
                theme.getDesc(),
                theme.getPrice()
        );
    }
}
