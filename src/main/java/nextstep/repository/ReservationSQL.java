package nextstep.repository;

public enum ReservationSQL {
    INSERT("INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?)"),
    SELECT_BY_ID("SELECT id, date, time, name, theme_id FROM reservation WHERE id = ?"),
    SELECT_BY_THEME_ID("SELECT id, date, time, name, theme_id FROM reservation WHERE theme_id = ?"),
    COUNT_BY_DATE_AND_TIME("SELECT count(*) FROM reservation WHERE date=? AND time=?"),
    DELETE_BY_ID("DELETE FROM reservation WHERE id = ?");

    private final String sql;

    ReservationSQL(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return sql;
    }
}
