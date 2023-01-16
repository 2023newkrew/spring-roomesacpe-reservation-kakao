package nextstep.mapper;

import nextstep.Theme;
import nextstep.dto.ThemeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "Spring")
public interface ThemeMapper {
    ThemeMapper INSTANCE = Mappers.getMapper(ThemeMapper.class);


    ThemeDTO toDto(Theme theme);

    Theme fromDto(ThemeDTO dto);
}
