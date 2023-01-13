package roomescape.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import roomescape.domain.Theme;
import roomescape.dto.ThemeRequest;
import roomescape.dto.ThemeResponse;

@Mapper
public interface ThemeMapper {
    ThemeMapper INSTANCE = Mappers.getMapper(ThemeMapper.class);

    @Mapping(target = "id", ignore = true)
    Theme themeRequestToTheme(ThemeRequest source);

    ThemeResponse themeToThemeResponse(Theme source);
}
