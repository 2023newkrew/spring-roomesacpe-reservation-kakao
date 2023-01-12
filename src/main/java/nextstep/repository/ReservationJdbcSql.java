package nextstep.repository;

public final class ReservationJdbcSql {
    public static final String FIND_BY_DATE_AND_TIME = "SELECT 1 FROM RESERVATION WHERE DATE = ? AND TIME = ? LIMIT 1";
    public static final String FIND_BY_ID = "SELECT * FROM RESERVATION WHERE ID = ?";
    public static final String DELETE_BY_ID = "DELETE FROM RESERVATION WHERE ID = ?";
    public static final String INSERT_INTO = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";


    private ReservationJdbcSql() {
    }


}
