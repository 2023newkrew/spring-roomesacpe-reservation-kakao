package nextstep.web.repository.database.mappingstrategy;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMappingStrategy<T> {

    T map(ResultSet rs) throws SQLException;
}
