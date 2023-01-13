package nextstep.mapper;

import nextstep.Reservation;
import nextstep.dto.ReservationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "Spring")
public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);


    ReservationDTO toDto(Reservation reservation);

    Reservation fromDto(ReservationDTO dto);
}