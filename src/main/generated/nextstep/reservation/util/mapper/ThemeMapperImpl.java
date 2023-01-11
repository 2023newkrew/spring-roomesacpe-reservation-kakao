package nextstep.reservation.util.mapper;

import javax.annotation.processing.Generated;
import nextstep.reservation.dto.ThemeRequestDto;
import nextstep.reservation.dto.ThemeResponseDto;
import nextstep.reservation.entity.Theme;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-11T18:20:49+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class ThemeMapperImpl implements ThemeMapper {

    @Override
    public Theme requestDtoToTheme(ThemeRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        Theme.ThemeBuilder theme = Theme.builder();

        theme.name( requestDto.getName() );
        theme.desc( requestDto.getDesc() );
        theme.price( requestDto.getPrice() );

        return theme.build();
    }

    @Override
    public ThemeResponseDto themeToThemeResponseDto(Theme theme) {
        if ( theme == null ) {
            return null;
        }

        ThemeResponseDto.ThemeResponseDtoBuilder themeResponseDto = ThemeResponseDto.builder();

        themeResponseDto.id( theme.getId() );
        themeResponseDto.name( theme.getName() );
        themeResponseDto.desc( theme.getDesc() );
        themeResponseDto.price( theme.getPrice() );

        return themeResponseDto.build();
    }
}
