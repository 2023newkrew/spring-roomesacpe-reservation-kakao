package nextstep.dao;

import nextstep.dto.ReservationDTO;
import nextstep.dto.ReservationRequest;

public interface ReservationDAO {

    Long insertIfNotExistsDateTime(ReservationDTO dto);

    ReservationDTO getById(Long id);

    void deleteById(Long id);
}
