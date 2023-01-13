package nextstep.web.theme.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.domain.Theme;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindAllThemeResponseDto {

    private List<Theme> themes;
}
