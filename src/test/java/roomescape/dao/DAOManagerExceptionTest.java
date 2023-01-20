package roomescape.dao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.test.context.ActiveProfiles;
import roomescape.connection.ConnectionManager;

@DisplayName("연결 매니저 예외처리 테스트")
@JdbcTest
@ActiveProfiles("test")
public class DAOManagerExceptionTest {

    @Autowired
    private DataSource dataSource;

    private ConnectionManager connectionManager;

    private static PreparedStatementCreator getInvalidPreparedStatementCreator() {
        return con -> con.prepareStatement("CREATE TABLE ?");
    }

    @BeforeEach
    void setUp() {
        connectionManager = new ConnectionManager(dataSource);
    }

    @DisplayName("잘못된 쿼리")
    @Test
    void failToQuery() {
        assertThatThrownBy(
                () -> connectionManager
                        .query(getInvalidPreparedStatementCreator(), rs -> null))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("잘못된 업데이트")
    @Test
    void failToUpdate() {
        assertThatThrownBy(
                () -> connectionManager
                        .update(getInvalidPreparedStatementCreator()))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("잘못된 업데이트 및 키 가져오기")
    @Test
    void failToUpdateAndGetKey() {
        assertThatThrownBy(
                () -> connectionManager
                        .updateAndGetKey(getInvalidPreparedStatementCreator(), "id", Long.class))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("연결 실패")
    @Test
    void failToConnection() {
        assertThatThrownBy(
                () -> new ConnectionManager(null)
                        .updateAndGetKey(
                                getInvalidPreparedStatementCreator(),
                                "id", Long.class))
                .isInstanceOf(RuntimeException.class);
    }
}
