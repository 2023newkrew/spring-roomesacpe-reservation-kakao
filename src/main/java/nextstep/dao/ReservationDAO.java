package nextstep.dao;

import nextstep.dto.ReservationDTO;
import nextstep.dto.ReservationRequest;

import java.sql.Time;
import java.sql.Date;

public interface ReservationDAO {

    Boolean existsByDateAndTime(Date date, Time time);

    Long insert(ReservationDTO dto);

    ReservationDTO getById(Long id);

    void deleteById(Long id);
}
