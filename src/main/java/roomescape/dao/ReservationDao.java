package roomescape.dao;

import roomescape.domain.Reservation;

import java.util.List;

public interface ReservationDao {
    String selectById = "SELECT * FROM reservation WHERE id = ?";
    String insert = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?)";
    String deleteById = "DELETE From reservation WHERE id = ?";
    String selectByDateAndTime = "SELECT * FROM reservation WHERE date = ? and time = ?";

    Long addReservation(Reservation reservation); // 예약을 db에 추가한다

    List<Reservation> findReservationById(Long id); // id로 예약을 조회한다

    List<Reservation> findReservationByDateAndTime(String date, String time); // date와 time으로 예약을 조회한다

    int removeReservation(Long id); // id로 예약을 삭제한다
}
