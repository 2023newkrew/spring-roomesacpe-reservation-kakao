package nextstep.repository;

import nextstep.dto.ReservationRequestDTO;
import nextstep.entity.Reservation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository {

    Long save(ReservationRequestDTO reservationRequestDTO) throws SQLException;

    Reservation findById(Long id) throws SQLException;

    boolean existByDateAndTime(LocalDate date, LocalTime time) throws SQLException;

    int deleteById(Long id) throws SQLException;
}
