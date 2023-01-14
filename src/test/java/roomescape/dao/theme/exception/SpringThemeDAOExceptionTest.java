package roomescape.dao.theme.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import roomescape.dao.theme.SpringThemeDAO;
import roomescape.dao.theme.ThemeDAO;

@DisplayName("JDBC 데이터베이스 접근 예외처리")
@JdbcTest
@ActiveProfiles("test")
@Sql("classpath:/test.sql")
public class SpringThemeDAOExceptionTest {

    private ThemeDAO themeDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        themeDAO = new SpringThemeDAO(jdbcTemplate);
    }

    @DisplayName("테마 조회) 아이디가 존재하지 않는 경우 null")
    @Test
    void findTheme() {
        assertThat(themeDAO.find(10L)).isNull();
    }
}
