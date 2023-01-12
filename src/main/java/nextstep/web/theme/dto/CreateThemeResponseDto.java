package nextstep.web.theme.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nextstep.domain.Theme;

@Getter
@RequiredArgsConstructor
public class CreateThemeResponseDto {
    private final String location;

    public static CreateThemeResponseDto from(Long id) {
        return new CreateThemeResponseDto(Theme.BASE_URL + "/" + id);
    }
}
