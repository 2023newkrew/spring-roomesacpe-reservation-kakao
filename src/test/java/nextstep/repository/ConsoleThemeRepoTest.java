package nextstep.repository;

import nextstep.domain.theme.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleThemeRepoTest {
    private ConsoleThemeRepo consoleThemeRepo = new ConsoleThemeRepo();
    private Theme testTheme = new Theme(
            1L,
            "name",
            "desc",
            10000);

    @BeforeEach
    void setUp() {
        Connection con = ConsoleConnection.connect();
        PreparedStatement ps = null;

        String sql = "TRUNCATE TABLE theme";
        int result = 0;

        try {
            ps = con.prepareStatement(sql);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @DisplayName("can store theme")
    @Test
    void can_store_theme() {
        long id = this.consoleThemeRepo.save(testTheme);
        assertNotNull(id);
    }

    @DisplayName("can find by id")
    @Test
    void can_find_by_id() {
        long id = this.consoleThemeRepo.save(testTheme);
        Theme theme = this.consoleThemeRepo.findById(id);
        assertTrue(theme.equals(this.testTheme));
    }

    @DisplayName("can return null for nonexistent id")
    @Test
    void can_return_null_for_nonexistent_id() {
        long nonexistentId = this.consoleThemeRepo.save(this.testTheme) + 1;
        Theme nonexistent = this.consoleThemeRepo.findById(nonexistentId);
        assertNull(nonexistent);
    }

    @DisplayName("can find all")
    @Test
    void can_find_all() {
        int CYCLE = 17;
        for (int i = 0; i < CYCLE; i++) {
            this.consoleThemeRepo.save(this.testTheme);
        }
        List<Theme> themes = this.consoleThemeRepo.findAll();
        assertEquals(CYCLE, themes.size());
        for (int i = 0; i < CYCLE; i++) {
            Theme theme = themes.get(i);
            assertTrue(theme.equals(this.testTheme));
        }
    }

    @DisplayName("can delete by id")
    @Test
    void can_delete_by_id() {
        long id = this.consoleThemeRepo.save(this.testTheme);
        int code = this.consoleThemeRepo.delete(id);
        assertEquals(1, code);

        Theme theme = consoleThemeRepo.findById(id);
        assertNull(theme);
    }
}
