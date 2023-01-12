package roomescape.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Mapper
public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    Reservation reservationRequestToReservation(ReservationRequest source);

    @Mapping(target = "time", dateFormat = "HH:mm")
    @Mapping(target = "themeName", source = "theme.name")
    @Mapping(target = "themeDesc", source = "theme.desc")
    @Mapping(target = "themePrice", source = "theme.price")
    ReservationResponse reservationToReservationResponse(Reservation source);
}
