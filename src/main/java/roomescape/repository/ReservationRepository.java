package roomescape.repository;

import roomescape.domain.Reservation;

public interface ReservationRepository {
    static final String CHECK_SCHEDULE_SQL = "SELECT COUNT(*) FROM reservation WHERE `date` = ? AND `time` = ?;";
    static final String FIND_RESERVATION_SQL = "SELECT r.*, t.id AS tid FROM reservation r, theme t WHERE r.id = ? AND r.theme_name = t.name;";
    static final String REMOVE_RESERVATION_SQL = "DELETE FROM reservation WHERE id = ?;";

    public Long addReservation(Reservation reservation);
    public int checkSchedule(String date, String time);
    public Reservation findReservation(Long id);
    public int removeReservation(Long id);

}
