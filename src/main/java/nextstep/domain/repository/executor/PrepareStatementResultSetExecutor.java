package nextstep.domain.repository.executor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface PrepareStatementResultSetExecutor<T> {

    T execute(PreparedStatement pstmt, ResultSet rs) throws SQLException;

}
