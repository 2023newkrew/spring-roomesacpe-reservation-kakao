package nextstep.dao;

import nextstep.dto.ReservationDTO;

import java.sql.Date;
import java.sql.Time;

public class SimpleReservationDAO implements ReservationDAO{

    @Override
    public Boolean existsByDateAndTime(Date date, Time time) {
        return null;
    }

    @Override
    public Long insert(ReservationDTO dto) {
        return null;
    }

    @Override
    public ReservationDTO getById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
