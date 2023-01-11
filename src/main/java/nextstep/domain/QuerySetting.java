package nextstep.domain;

public class QuerySetting {

    public static class Reservation {
        public static final String TABLE_NAME = "RESERVATION";
        public static final String PK_NAME = "id";
        public static final String INSERT = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        public static final String SELECT_BY_ID = "SELECT * FROM reservation WHERE id = ?";
        public static final String SELECT_COUNT_BY_DATE_AND_TIME = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ?";
        public static final String DELETE_BY_ID = "DELETE FROM reservation WHERE id = ?";
    }

    public static class Theme {
        public static final String TABLE_NAME = "THEME";
        public static final String PK_NAME = "id";
        public static final String SELECT_BY_NAME = "SELECT * FROM theme WHERE name = ?";
    }

}
