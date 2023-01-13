package nextstep.mapstruct;

import nextstep.entity.Theme;
import nextstep.dto.ThemeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ThemeMapper {

    ThemeMapper INSTANCE = Mappers.getMapper(ThemeMapper.class);

    ThemeResponseDto themeToThemeResponseDto(Theme theme);

}
