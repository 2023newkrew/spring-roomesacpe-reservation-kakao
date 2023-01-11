package nextstep.reservation.util.mapper;

import nextstep.reservation.dto.ThemeRequestDto;
import nextstep.reservation.dto.ThemeResponseDto;
import nextstep.reservation.entity.Theme;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ThemeMapper {
    @Mapping(target = "id", ignore = true)
    Theme requestDtoToTheme(ThemeRequestDto requestDto);

    ThemeResponseDto themeToThemeResponseDto(Theme theme);
}
