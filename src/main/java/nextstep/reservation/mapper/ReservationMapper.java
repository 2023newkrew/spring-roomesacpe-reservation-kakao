package nextstep.reservation.mapper;

import nextstep.reservation.domain.Reservation;
import nextstep.reservation.dto.ReservationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "Spring")
public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);


    ReservationDTO toDto(Reservation reservation);

    Reservation fromDto(ReservationDTO dto);
}