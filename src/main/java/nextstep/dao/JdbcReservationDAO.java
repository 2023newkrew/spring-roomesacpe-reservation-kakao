package nextstep.dao;

import nextstep.dto.ReservationDTO;
import org.springframework.stereotype.Component;

@Component
public class JdbcReservationDAO implements ReservationDAO{

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
