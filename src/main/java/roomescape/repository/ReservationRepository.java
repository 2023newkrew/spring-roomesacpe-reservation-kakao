package roomescape.repository;

import roomescape.domain.Reservation;

public interface ReservationRepository {
    String CHECK_SCHEDULE_SQL = "SELECT COUNT(*) FROM reservation WHERE `date` = ? AND `time` = ?;";
    String FIND_RESERVATION_SQL = "SELECT r.*, t.id AS tid FROM reservation r, theme t WHERE r.id = ? AND r.theme_name = t.name;";
    String REMOVE_RESERVATION_SQL = "DELETE FROM reservation WHERE id = ?;";

    Long addReservation(Reservation reservation);
    int checkSchedule(String date, String time);
    Reservation findReservation(Long id);
    int removeReservation(Long id);

}
