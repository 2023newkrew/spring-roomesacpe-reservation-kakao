package nextstep.main.java.nextstep.mvc.domain.theme;

import nextstep.main.java.nextstep.mvc.domain.theme.response.ThemeFindResponse;
import org.springframework.stereotype.Component;

@Component
public class ThemeMapper {
    public ThemeFindResponse themeToFindResponse(Theme theme) {
        return ThemeFindResponse.builder()
                .id(theme.getId())
                .name(theme.getName())
                .desc(theme.getDesc())
                .price(theme.getPrice())
                .build();
    }
}
