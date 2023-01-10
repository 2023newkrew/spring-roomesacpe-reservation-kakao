package nextstep.domain.reservation.repository.executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PrepareStatementExecutor<T> {

    T execute(PreparedStatement pstmt) throws SQLException;

}
