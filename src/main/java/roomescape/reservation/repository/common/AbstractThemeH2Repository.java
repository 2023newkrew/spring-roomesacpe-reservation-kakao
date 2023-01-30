package roomescape.reservation.repository.common;

public abstract class AbstractThemeH2Repository implements ThemeRepository {
    protected String insertQuery = "INSERT INTO THEME (name, desc, price) VALUES (?, ?, ?)";
    protected String selectQuery = "SELECT * FROM THEME";
    protected String selectByIdQuery = "SELECT * FROM THEME WHERE id = ?";
    protected String selectByNameQuery = "SELECT * FROM THEME WHERE name = ?";
    protected String deleteByIdQuery = "DELETE FROM THEME WHERE id = ?";
}
