package roomescape.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Mapper
public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Mapping(target = "id", ignore = true)
    Reservation reservationRequestToReservation(ReservationRequest source);

    @Mapping(target = "id", source = "reservation.id")
    @Mapping(target = "date", source = "reservation.date")
    @Mapping(target = "time", source = "reservation.time", dateFormat = "HH:mm")
    @Mapping(target = "name", source = "reservation.name")
    @Mapping(target = "themeName", source = "theme.name")
    @Mapping(target = "themeDesc", source = "theme.desc")
    @Mapping(target = "themePrice", source = "theme.price")
    ReservationResponse reservationToReservationResponse(Reservation reservation, Theme theme);
}
