package nextstep.repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.dto.ReservationRequestDTO;
import nextstep.entity.Reservation;

public interface ReservationRepository {

    Reservation save(ReservationRequestDTO reservationRequestDTO) throws SQLException;

    Reservation findById(Long id) throws SQLException;

    boolean existByDateAndTime(LocalDate date, LocalTime time) throws SQLException;

    int deleteById(Long id) throws SQLException;
}
