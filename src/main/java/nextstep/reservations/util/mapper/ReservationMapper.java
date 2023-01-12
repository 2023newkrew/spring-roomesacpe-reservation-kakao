package nextstep.reservations.util.mapper;

import nextstep.reservations.domain.entity.reservation.Reservation;
import nextstep.reservations.dto.reservation.ReservationRequestDto;
import nextstep.reservations.dto.reservation.ReservationResponseDto;
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
    Reservation requestDtoToReservation(ReservationRequestDto requestDto);

    @Mappings({
            @Mapping(source = "theme.name", target = "themeName"),
            @Mapping(source = "theme.desc", target = "themeDesc"),
            @Mapping(source = "theme.price", target = "themePrice")
    })
    ReservationResponseDto reservationToResponseDto(Reservation reservation);
}
