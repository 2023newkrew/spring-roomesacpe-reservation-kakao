package nextstep.mapstruct;

import nextstep.dto.ReservationResponseDTO;
import nextstep.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Mappings({
            @Mapping(target = "themeName", source = "theme.name"),
            @Mapping(target = "themeDescription", source = "theme.description"),
            @Mapping(target = "themePrice", source = "theme.price")
    })
    ReservationResponseDTO reservationToResponseDTO(Reservation reservation);

}
