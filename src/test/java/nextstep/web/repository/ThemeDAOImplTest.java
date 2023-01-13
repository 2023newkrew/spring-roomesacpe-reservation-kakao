package nextstep.web.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import nextstep.domain.Theme;
import nextstep.web.repository.ThemeDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class ThemeDAOImplTest {
    private ThemeDAOImpl themeDAOImpl;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        themeDAOImpl = new ThemeDAOImpl(jdbcTemplate);
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
        Theme theme = themeDAOImpl.findById(2L);

        assertThat(theme).isNotNull();
        assertThat(theme.getName()).isEqualTo("테마이름2");
    }

    @Test
    @DisplayName("이름을 통해 테마 정보를 잘 받아오는지 테스트")
    void findByNameTest() {
        Theme theme = themeDAOImpl.findByName("테마이름2");

        assertThat(theme).isNotNull();
        assertThat(theme.getPrice()).isEqualTo(21000);
    }

    @Test
    @DisplayName("테마를 생성하고 ID를 잘 반환하는지 테스트")
    void insertWithKeyHolderTest() {
        Theme theme = new Theme("테마이름3", "테마설명", 20000);
        Long id = themeDAOImpl.insertWithKeyHolder(theme);
        assertThat(id).isNotNull();

        Theme themeById = themeDAOImpl.findById(id);
        assertThat(themeById).isNotNull();
        assertThat(themeById.getName()).isEqualTo("테마이름3");
    }

    @Test
    @DisplayName("테마 목록을 잘 받아오는지 테스트")
    void getAllThemesTest() {
        List<Theme> themes = themeDAOImpl.getAllThemes();
        assertThat(themes).hasSize(2);
        assertThat(themes.get(0).getName()).isEqualTo("테마이름");
        assertThat(themes.get(1).getPrice()).isEqualTo(21000);
    }

    @Test
    @DisplayName("ID를 통해 테마를 잘 삭제하는지 테스트")
    void deleteTest() {
        int rowNum = themeDAOImpl.delete(1L);
        assertThat(rowNum).isEqualTo(1);
    }
}
