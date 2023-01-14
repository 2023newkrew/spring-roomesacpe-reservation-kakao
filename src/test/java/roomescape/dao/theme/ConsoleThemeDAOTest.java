package roomescape.dao.theme;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import roomescape.dto.Theme;

@DisplayName("콘솔용 데이터베이스 접근 테스트")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql("classpath:/test.sql")
public class ConsoleThemeDAOTest {

    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static final String NAME_DATA1 = "워너고홈";
    private static final String NAME_DATA2 = "테스트";
    private static final String NAME_DATA3 = "테스트 이름";
    private static final String DESC_DATA = "병맛 어드벤처 회사 코믹물";
    private static final int PRICE_DATA = 29000;

    private static final String COUNT_SQL = "SELECT count(*) FROM THEME";

    private final ThemeDAO themeDAO = new ConsoleThemeDAO(URL, USER, PASSWORD);
    private Connection con;

    @BeforeEach
    void setUp() throws SQLException {
        con = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @AfterEach
    void setDown() throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    private <T> T getCount(Class<T> tClass) throws SQLException {
        ResultSet resultSet = con.createStatement().executeQuery(COUNT_SQL);
        assertThat(resultSet.next()).isTrue();
        return resultSet.getObject(1, tClass);
    }

    @DisplayName("테마 생성")
    @Test
    void insertTheme() throws SQLException {
        long count = getCount(Long.class);

        Theme theme = new Theme(NAME_DATA3, DESC_DATA, PRICE_DATA);

        themeDAO.create(theme);

        long actual = getCount(Long.class);
        assertThat(actual).isEqualTo(count + 1L);
    }

    @DisplayName("테마 조회")
    @Test
    void findTheme() {
        Theme theme = themeDAO.find(1L);

        assertThat(theme.getName()).isEqualTo(NAME_DATA1);
        assertThat(theme.getDesc()).isEqualTo(DESC_DATA);
        assertThat(theme.getPrice()).isEqualTo(PRICE_DATA);
    }

    @DisplayName("테마 목록 조회")
    @Test
    void listTheme() throws SQLException {
        int count = getCount(Integer.class);

        List<Theme> theme = themeDAO.list();

        assertThat(theme.size()).isEqualTo(count);
        assertThat(theme.get(0).getName()).isEqualTo(NAME_DATA1);
        assertThat(theme.get(0).getDesc()).isEqualTo(DESC_DATA);
        assertThat(theme.get(0).getPrice()).isEqualTo(PRICE_DATA);
    }

    @DisplayName("테마 삭제")
    @Test
    void removeTheme() throws SQLException {
        long count = getCount(Long.class);

        themeDAO.remove(1L);

        long actual = getCount(Long.class);
        assertThat(actual).isEqualTo(count - 1L);
    }

    @DisplayName("테마 존재 확인")
    @Test
    void existTheme() {
        Theme theme1 = new Theme(NAME_DATA1, DESC_DATA, PRICE_DATA);
        Theme theme2 = new Theme(NAME_DATA2, DESC_DATA, PRICE_DATA);
        Theme theme3 = new Theme(NAME_DATA3, DESC_DATA, PRICE_DATA);

        assertThat(themeDAO.exist(theme1)).isTrue();
        assertThat(themeDAO.exist(theme2)).isTrue();
        assertThat(themeDAO.exist(theme3)).isFalse();
    }

    @DisplayName("테마 아이디 존재 확인")
    @Test
    void existReservationId() {
        assertThat(themeDAO.existId(1L)).isTrue();
        assertThat(themeDAO.existId(2L)).isTrue();
        assertThat(themeDAO.existId(3L)).isFalse();
    }
}
