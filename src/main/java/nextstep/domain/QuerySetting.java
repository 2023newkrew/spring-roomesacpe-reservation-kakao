package nextstep.domain;

public class QuerySetting {

    public static class Reservation {
        public static final String TABLE_NAME = "RESERVATION";
        public static final String PK_NAME = "id";
        public static final String INSERT = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
        public static final String SELECT_BY_ID = "SELECT r.id as r_id, r.date, r.time, r.name as r_name, t.name as t_name, t.desc, t.price FROM reservation r INNER JOIN theme t ON r.theme_id = t.id WHERE r.id = ?";
        public static final String SELECT_COUNT_BY_THEME_ID = "SELECT COUNT(*) FROM reservation WHERE theme_id = ? LIMIT 1";
        public static final String SELECT_COUNT_BY_THEME_ID_AND_DATE_AND_TIME = "SELECT COUNT(*) FROM reservation WHERE theme_id = ? AND date = ? AND time = ?";
        public static final String DELETE_BY_ID = "DELETE FROM reservation WHERE id = ?";
    }

    public static class Theme {
        public static final String TABLE_NAME = "THEME";
        public static final String PK_NAME = "id";
        public static final String SELECT_BY_NAME = "SELECT * FROM theme WHERE name = ?";
        public static final String SELECT_ALL = "SELECT * FROM theme LIMIT ? OFFSET ?";
        public static final String DELETE_BY_ID = "DELETE FROM theme WHERE id = ?";
        public static final String UPDATE_BY_ID = "UPDATE theme SET name = ?, desc = ?, price = ? WHERE id = ?";
    }

}
