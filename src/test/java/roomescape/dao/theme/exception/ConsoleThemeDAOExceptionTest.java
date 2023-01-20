package roomescape.dao.theme.exception;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ActiveProfiles;
import roomescape.connection.ConnectionManager;
import roomescape.dao.theme.ConsoleThemeDAO;
import roomescape.dao.theme.ThemeDAO;

@DisplayName("콘솔용 데이터베이스 접근 예외처리")
@JdbcTest
@ActiveProfiles("test")
public class ConsoleThemeDAOExceptionTest {

    @Autowired
    private DataSource dataSource;

    private ThemeDAO themeDAO;

    @BeforeEach
    void setUp() {
        ConnectionManager connectionManager = new ConnectionManager(dataSource);
        themeDAO = new ConsoleThemeDAO(connectionManager);
    }

    @DisplayName("테마 조회) 아이디가 존재하지 않는 경우 null")
    @Test
    void returnNullWhenFindNotExistingId() {
        assertThat(themeDAO.find(10L)).isNull();
    }
}
