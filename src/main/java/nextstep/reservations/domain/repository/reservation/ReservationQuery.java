package nextstep.reservations.domain.repository.reservation;

public enum ReservationQuery {
    INSERT_ONE("INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?)"),
    FIND_BY_ID("SELECT * FROM reservation WHERE id = ?"),
    REMOVE_BY_ID("DELETE FROM reservation WHERE id = ?"),
    TRUNCATE("TRUNCATE TABLE reservation");

    private final String value;

    ReservationQuery(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }
}
