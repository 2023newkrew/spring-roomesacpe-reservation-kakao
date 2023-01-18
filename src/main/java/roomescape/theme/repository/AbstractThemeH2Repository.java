package roomescape.theme.repository;

abstract class AbstractThemeH2Repository implements ThemeRepository {
    String insertQuery = "INSERT INTO THEME (name, desc, price) VALUES (?, ?, ?)";
    String selectQuery = "SELECT * FROM THEME";
    String selectByIdQuery = "SELECT * FROM THEME WHERE id = ?";
    String selectByNameQuery = "SELECT * FROM THEME WHERE name = ?";
    String deleteByIdQuery = "DELETE FROM THEME WHERE id = ?";
}
