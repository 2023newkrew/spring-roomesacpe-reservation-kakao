package nextstep.reservation.mapper;

import nextstep.reservation.domain.Reservation;
import nextstep.reservation.domain.Theme;
import nextstep.reservation.dto.ReservationDTO;
import nextstep.reservation.dto.ReservationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "Spring")
public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "request.name")
    Reservation fromRequest(ReservationRequest request, Theme theme);

    ReservationDTO toDto(Reservation reservation);

    Reservation fromDto(ReservationDTO dto);
}