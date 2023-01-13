package roomescape.dao.theme;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import roomescape.dto.Theme;

@DisplayName("JDBC 데이터베이스 접근 테스트")
@JdbcTest
@Sql("classpath:/test.sql")
public class SpringThemeDAOTest {

    private static final String NAME_DATA1 = "워너고홈";
    private static final String NAME_DATA2 = "테스트 이름";
    private static final String DESC_DATA = "병맛 어드벤처 회사 코믹물";
    private static final int PRICE_DATA = 29000;

    private static final String COUNT_SQL = "SELECT count(*) FROM THEME";

    private ThemeDAO dao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        dao = new SpringThemeDAO(jdbcTemplate);
    }

    @DisplayName("테마 생성")
    @Test
    void addReservation() {
        Theme theme = new Theme(NAME_DATA2, DESC_DATA, PRICE_DATA);

        dao.insert(theme);

        Long count = jdbcTemplate.queryForObject(COUNT_SQL, Long.class);
        assertThat(count).isEqualTo(2L);
    }

    @DisplayName("테마 조회")
    @Test
    void findReservation() {

    }

    @DisplayName("테마 삭제")
    @Test
    void deleteReservation() {

    }
}
