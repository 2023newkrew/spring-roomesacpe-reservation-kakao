package nextstep.reservations.domain.repository.theme;

public enum ThemeQuery {
    INSERT_ONE("INSERT INTO theme (name, desc, price) VALUES (?, ?, ?)"),
    FIND_ALL("SELECT * FROM theme"),
    REMOVE_BY_ID("DELETE FROM theme WHERE id = ?");

    private final String query;

    ThemeQuery(String query) {
        this.query = query;
    }

    public String get() {
        return query;
    }
}
