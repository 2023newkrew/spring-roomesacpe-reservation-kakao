package roomescape.dao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("연결 매니저 예외처리 테스트")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql("classpath:/test.sql")
public class DAOManagerExceptionTest {

    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private final DAOManager daoManager = new DAOManager(URL, USER, PASSWORD);

    private static PreparedStatementCreator getInvalidPreparedStatementCreator() {
        return con -> con.prepareStatement("CREATE TABLE ?");
    }

    @DisplayName("잘못된 쿼리")
    @Test
    void failToQuery() {
        assertThatThrownBy(
                () -> daoManager
                        .query(getInvalidPreparedStatementCreator(), null))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("잘못된 업데이트")
    @Test
    void failToUpdate() {
        assertThatThrownBy(
                () -> daoManager
                        .update(getInvalidPreparedStatementCreator()))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("잘못된 업데이트 및 키 가져오기")
    @Test
    void failToUpdateAndGetKey() {
        assertThatThrownBy(
                () -> daoManager
                        .updateAndGetKey(getInvalidPreparedStatementCreator(), "id", Long.class))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("연결 실패")
    @Test
    void failToConnection() {
        assertThatThrownBy(
                () -> new DAOManager("", "", "")
                        .updateAndGetKey(getInvalidPreparedStatementCreator(), "id", Long.class))
                .isInstanceOf(RuntimeException.class);
    }
}
