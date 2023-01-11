package nextstep.reservation.util.mapper;

import javax.annotation.processing.Generated;
import nextstep.reservation.dto.ThemeRequestDto;
import nextstep.reservation.entity.Theme;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-11T16:03:44+0900",
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
}
