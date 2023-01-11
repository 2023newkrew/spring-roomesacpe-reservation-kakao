package nextstep.reservation.util.mapper;

import nextstep.reservation.dto.ReservationRequestDto;
import nextstep.reservation.dto.ReservationResponseDto;
import nextstep.reservation.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    @Mappings({
            @Mapping(source = "themeName", target = "theme.name"),
            @Mapping(source = "themeDesc", target = "theme.desc"),
            @Mapping(source = "themePrice", target = "theme.price"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "theme", ignore = true)
    })
    Reservation RequestDtoToReservation(ReservationRequestDto requestDto);

    @Mappings({
            @Mapping(source = "theme.name", target = "themeName"),
            @Mapping(source = "theme.desc", target = "themeDesc"),
            @Mapping(source = "theme.price", target = "themePrice")
    })
    ReservationResponseDto ReservationToResponseDto(Reservation reservation);
}
