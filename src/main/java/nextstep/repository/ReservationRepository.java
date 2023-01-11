package nextstep.repository;

import lombok.AllArgsConstructor;
import nextstep.Reservation;
import nextstep.dao.ReservationDAO;
import nextstep.dto.ReservationDTO;
import org.springframework.stereotype.Repository;

public interface ReservationRepository {

    Long insertIfNotExistsDateTime(Reservation reservation);

    Reservation getById(Long id);

    void deleteById(Long id);
}
