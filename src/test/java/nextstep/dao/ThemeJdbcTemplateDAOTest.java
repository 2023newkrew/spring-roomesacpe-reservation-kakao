package nextstep.dao;

import nextstep.domain.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ThemeJdbcTemplateDAOTest {
    private ThemeJdbcTemplateDAO themeJdbcTemplateDAO;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        themeJdbcTemplateDAO = new ThemeJdbcTemplateDAO(jdbcTemplate);
    }

    @DisplayName("테마 생성")
    @Test
    void save() {
        Theme theme = new Theme("테마이름3", "테마설명", 22_000);
        Long id = themeJdbcTemplateDAO.save(theme);

        assertThat(id).isNotNull();
    }

    @DisplayName("전체 테마 조회")
    @Test
    void findAll() {
        List<Theme> themes = themeJdbcTemplateDAO.findAll();

        assertThat(themes).hasSize(2);
        assertThat(themes.get(0).getName()).isEqualTo("테마이름");
        assertThat(themes.get(1).getName()).isEqualTo("테마이름2");
    }

    @DisplayName("테마 삭제")
    @Test
    void deleteById() {
        int rowCount = themeJdbcTemplateDAO.deleteById(1L);

        assertThat(rowCount).isEqualTo(1);
    }
}