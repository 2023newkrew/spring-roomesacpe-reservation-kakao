package roomescape.dao.theme.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.theme.ConsoleThemeDAO;
import roomescape.dao.theme.ThemeDAO;

@DisplayName("콘솔용 데이터베이스 접근 예외처리")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
@Sql("classpath:/test.sql")
public class ConsoleThemeDAOExceptionTest {

    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private final ThemeDAO themeDAO = new ConsoleThemeDAO(URL, USER, PASSWORD);

    @DisplayName("테마 조회) 아이디가 존재하지 않는 경우 null")
    @Test
    void returnNullWhenFindNotExistingId() {
        assertThat(themeDAO.find(10L)).isNull();
    }
}
