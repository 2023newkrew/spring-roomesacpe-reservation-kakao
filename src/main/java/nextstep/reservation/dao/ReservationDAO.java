package nextstep.reservation.dao;

import nextstep.reservation.dto.ReservationDTO;

import java.sql.Date;
import java.sql.Time;

public interface ReservationDAO {

    Boolean existsByDateAndTime(Date date, Time time);

    Long insert(ReservationDTO dto);

    ReservationDTO getById(Long id);

    Boolean deleteById(Long id);
}
