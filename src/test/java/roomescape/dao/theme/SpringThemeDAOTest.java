package roomescape.dao.theme;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import roomescape.dto.Theme;

@DisplayName("JDBC 데이터베이스 접근 테스트")
@JdbcTest
@ActiveProfiles("test")
@Sql("classpath:/test.sql")
public class SpringThemeDAOTest {

    private static final String NAME_DATA1 = "워너고홈";
    private static final String NAME_DATA2 = "테스트 이름";
    private static final String DESC_DATA = "병맛 어드벤처 회사 코믹물";
    private static final int PRICE_DATA = 29000;

    private static final String COUNT_SQL = "SELECT count(*) FROM THEME";

    private ThemeDAO themeDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        themeDAO = new SpringThemeDAO(jdbcTemplate);
    }

    @DisplayName("테마 생성")
    @Test
    void createTheme() {
        Theme theme = new Theme(NAME_DATA2, DESC_DATA, PRICE_DATA);

        themeDAO.create(theme);

        Long count = jdbcTemplate.queryForObject(COUNT_SQL, Long.class);
        assertThat(count).isEqualTo(2L);
    }

    @DisplayName("테마 목록 조회")
    @Test
    void listTheme() {
        List<Theme> theme = themeDAO.list();

        assertThat(theme.size()).isEqualTo(1);
        assertThat(theme.get(0).getName()).isEqualTo(NAME_DATA1);
        assertThat(theme.get(0).getDesc()).isEqualTo(DESC_DATA);
        assertThat(theme.get(0).getPrice()).isEqualTo(PRICE_DATA);
    }

    @DisplayName("테마 삭제")
    @Test
    void removeTheme() {
        themeDAO.remove(1L);

        Long count = jdbcTemplate.queryForObject(COUNT_SQL, Long.class);
        assertThat(count).isEqualTo(0L);
    }

    @DisplayName("테마 존재 확인")
    @Test
    void existTheme() {
        Theme theme1 = new Theme(NAME_DATA1, DESC_DATA, PRICE_DATA);
        Theme theme2 = new Theme(NAME_DATA2, DESC_DATA, PRICE_DATA);

        assertThat(themeDAO.exist(theme1)).isTrue();
        assertThat(themeDAO.exist(theme2)).isFalse();
    }

    @DisplayName("테마 아이디 존재 확인")
    @Test
    void existReservationId() {
        assertThat(themeDAO.existId(1L)).isTrue();
        assertThat(themeDAO.existId(2L)).isFalse();
    }
}
