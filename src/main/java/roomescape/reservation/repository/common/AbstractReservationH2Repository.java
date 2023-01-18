package roomescape.reservation.repository.common;

public abstract class AbstractReservationH2Repository implements ReservationRepository {
    protected String insertQuery = "INSERT INTO RESERVATION (date, time, name, themeId) VALUES (?, ?, ?, ?)";
    protected String selectByIdQuery = "SELECT * FROM RESERVATION WHERE id = ?";
    protected String selectByDateAndTimeQuery = "SELECT * FROM RESERVATION WHERE date = ? and time = ?";
    protected String deleteByIdQuery = "DELETE From RESERVATION WHERE id = ?";

}
