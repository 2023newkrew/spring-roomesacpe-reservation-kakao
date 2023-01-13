package nextstep.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import nextstep.domain.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class WebThemeDAOTest {
    private WebThemeDAO webThemeDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        webThemeDAO = new WebThemeDAO(jdbcTemplate);
        jdbcTemplate.execute("DROP TABLE theme IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE theme("
                + "    id    bigint not null auto_increment,"
                + "    name  varchar(20),"
                + "    desc  varchar(255),"
                + "    price int,"
                + "    primary key (id)\n"
                + ")");
        List<Object[]> themes = List.of(
                new Object[]{"테마이름", "테마설명", 22000},
                new Object[]{"테마이름2", "테마설명", 21000}
        );
        jdbcTemplate.batchUpdate(
                "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);",
                themes);
    }

    @Test
    @DisplayName("ID를 통해 테마 정보를 잘 받아오는지 테스트")
    void findByIdTest() {
        Theme theme = webThemeDAO.findById(2L);

        assertThat(theme).isNotNull();
        assertThat(theme.getName()).isEqualTo("테마이름2");
    }

    @Test
    @DisplayName("이름을 통해 테마 정보를 잘 받아오는지 테스트")
    void findByNameTest() {
        List<Theme> theme = webThemeDAO.findByName("테마이름2");

        assertThat(theme).isNotNull();
        assertThat(theme.get(0).getPrice()).isEqualTo(21000);
    }

    @Test
    @DisplayName("")
    void insertWithKeyHolderTest() {
        Theme theme = new Theme("테마이름3", "테마설명", 20000);
        Long id = webThemeDAO.insertWithKeyHolder(theme);
        assertThat(id).isNotNull();

        Theme themeById = webThemeDAO.findById(id);
        assertThat(themeById).isNotNull();
        assertThat(themeById.getName()).isEqualTo("테마이름3");
    }

    @Test
    @DisplayName("테마 목록을 잘 받아오는지 테스트")
    void getAllThemesTest() {
        List<Theme> themes = webThemeDAO.getAllThemes();
        assertThat(themes).hasSize(2);
        assertThat(themes.get(0).getName()).isEqualTo("테마이름");
        assertThat(themes.get(1).getPrice()).isEqualTo(21000);
    }

    @Test
    @DisplayName("ID를 통해 테마를 잘 삭제하는지 테스트")
    void deleteTest() {
        int rowNum = webThemeDAO.delete(1L);
        assertThat(rowNum).isEqualTo(1);
    }
}
