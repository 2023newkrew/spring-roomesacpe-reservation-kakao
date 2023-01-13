package nextstep.repository;

public final class ReservationJdbcSql {
    public static final String FIND_BY_DATE_AND_TIME = "SELECT 1 FROM RESERVATION WHERE DATE = ? AND TIME = ? LIMIT 1";
    public static final String FIND_BY_ID = ""
            + "SELECT reservation.id, reservation.date, reservation.time, reservation.name, theme.name theme_name, theme.desc theme_desc, theme.price theme_price "
            + "FROM reservation, theme "
            + "WHERE reservation.id = ? AND reservation.id = theme.id";
    public static final String DELETE_BY_ID = "DELETE FROM RESERVATION WHERE ID = ?";
    public static final String INSERT_INTO = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";

    private ReservationJdbcSql() {
    }

}
