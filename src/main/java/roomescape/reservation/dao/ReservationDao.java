package roomescape.reservation.dao;

import roomescape.reservation.domain.Reservation;

import java.util.List;

public interface ReservationDao {
    String selectById = "SELECT * FROM reservation WHERE id = ?";
    String insert = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?)";
    String deleteById = "DELETE From reservation WHERE id = ?";
    String selectByDateAndTime = "SELECT * FROM reservation WHERE date = ? and time = ?";

    Reservation addReservation(Reservation reservation); // 예약을 db에 추가한다

    List<Reservation> findReservationById(Long id); // id로 예약을 조회한다

    List<Reservation> findReservationByDateAndTime(String date, String time); // date와 time으로 예약을 조회한다

    void removeReservation(Long id); // id로 예약을 삭제
}
