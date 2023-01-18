package roomescape.reservation.dao;

abstract class AbstractReservationH2Repository implements ReservationRepository {
    String insertQuery = "INSERT INTO RESERVATION (date, time, name, themeId) VALUES (?, ?, ?, ?)";
    String selectByIdQuery = "SELECT * FROM RESERVATION WHERE id = ?";
    String selectByDateAndTimeQuery = "SELECT * FROM RESERVATION WHERE date = ? and time = ?";
    String deleteByIdQuery = "DELETE From RESERVATION WHERE id = ?";

}
