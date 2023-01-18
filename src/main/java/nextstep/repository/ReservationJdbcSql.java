package nextstep.repository;

public final class ReservationJdbcSql {
    public static final String FIND_BY_DATE_AND_TIME_STATEMENT = "SELECT 1 FROM RESERVATION WHERE DATE = ? AND TIME = ? AND theme_id = ? LIMIT 1";
    public static final String FIND_BY_ID_STATEMENT =
            "SELECT \n"
            + "    reservation.id,\n"
            + "    reservation.date,\n"
            + "    reservation.time,\n"
            + "    reservation.name,\n"
            + "    theme.name AS theme_name,\n"
            + "    theme.desc AS theme_desc,\n"
            + "    theme.price AS theme_price \n"
            + "FROM reservation, theme \n"
            + "WHERE reservation.id = ? \n"
            + "    AND reservation.theme_id = theme.id";
    public static final String DELETE_BY_ID_STATEMENT = "DELETE FROM RESERVATION WHERE ID = ?";

    public static final String INSERT_INTO_STATEMENT = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?)";

    public static final String EXIST_BY_THEME_ID_STATEMENT = "SELECT DISTINCT theme_id  FROM RESERVATION WHERE theme_id = ? LIMIT 1";

    

    private ReservationJdbcSql() {
    }

}
