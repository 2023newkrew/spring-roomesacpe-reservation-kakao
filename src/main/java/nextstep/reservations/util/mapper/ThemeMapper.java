package nextstep.reservations.util.mapper;

import nextstep.reservations.domain.entity.theme.Theme;
import nextstep.reservations.dto.theme.ThemeRequestDto;
import nextstep.reservations.dto.theme.ThemeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ThemeMapper {
    @Mapping(target = "id", ignore = true)
    Theme requestDtoToTheme(ThemeRequestDto requestDto);

    ThemeResponseDto themeToThemeResponseDto(Theme theme);
}
