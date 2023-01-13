package roomescape.dao;

import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

import java.util.List;

public interface ReservationDao {
    String selectById = "SELECT * FROM reservation WHERE id = ?";
    String insert = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?)";
    String deleteById = "DELETE From reservation WHERE id = ?";
    String countByDateAndTime = "SELECT COUNT(*) FROM reservation WHERE date = ? and time = ?";

    Long addReservation(Reservation reservation);

    List<Reservation> findReservation(Long id);

    Integer checkSchedule(ReservationDto reservationDto);

    int removeReservation(Long id);
}
