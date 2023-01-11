package nextstep.dao;

import nextstep.dto.ReservationDTO;

public class SimpleReservationDAO implements ReservationDAO{

    @Override
    public Long insertIfNotExistsDateTime(ReservationDTO dto) {
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
