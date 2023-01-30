package nextstep.repository;

import nextstep.domain.theme.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class WebAppThemeRepoTest {
    @Autowired
    private WebAppThemeRepo webAppThemeRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Theme testTheme = new Theme(
            1L,
            "name",
            "desc",
            10000);

    @BeforeEach
    void setUp() {
        String sql = "TRUNCATE TABLE theme";
        jdbcTemplate.update(sql);
    }

    @DisplayName("can store theme")
    @Test
    void can_store_theme() {
        long id = this.webAppThemeRepo.save(testTheme);
        assertNotNull(id);
    }

    @DisplayName("can find by id")
    @Test
    void can_find_by_id() {
        long id = this.webAppThemeRepo.save(testTheme);
        Theme theme = this.webAppThemeRepo.findById(id);
        assertTrue(theme.equals(this.testTheme));
    }

    @DisplayName("can return null for nonexistent id")
    @Test
    void can_return_null_for_nonexistent_id() {
        long nonexistentId = this.webAppThemeRepo.save(this.testTheme) + 1;
        Theme nonexistent = this.webAppThemeRepo.findById(nonexistentId);
        assertNull(nonexistent);
    }

    @DisplayName("can find all")
    @Test
    void can_find_all() {
        int CYCLE = 17;
        for (int i = 0; i < CYCLE; i++) {
            this.webAppThemeRepo.save(this.testTheme);
        }
        List<Theme> themes = this.webAppThemeRepo.findAll();
        assertEquals(CYCLE, themes.size());
        for (int i = 0; i < CYCLE; i++) {
            Theme theme = themes.get(i);
            assertTrue(theme.equals(this.testTheme));
        }
    }

    @DisplayName("can delete by id")
    @Test
    void can_delete_by_id() {
        long id = this.webAppThemeRepo.save(this.testTheme);
        int code = this.webAppThemeRepo.delete(id);
        assertEquals(1, code);

        Theme theme = webAppThemeRepo.findById(id);
        assertNull(theme);
    }
}
