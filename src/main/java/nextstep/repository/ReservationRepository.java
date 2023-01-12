package nextstep.repository;

import nextstep.dto.ReservationRequestDTO;
import nextstep.entity.Reservation;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository {

    Long save(ReservationRequestDTO reservationRequestDTO) throws SQLException, DataAccessException;

    Reservation findById(Long id) throws SQLException, DataAccessException;

    boolean existByDateAndTime(LocalDate date, LocalTime time) throws SQLException, DataAccessException;

    int deleteById(Long id) throws SQLException, DataAccessException;
}
