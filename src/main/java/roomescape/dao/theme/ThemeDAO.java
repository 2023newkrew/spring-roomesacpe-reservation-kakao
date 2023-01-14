package roomescape.dao.theme;

import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import roomescape.dto.Theme;

public interface ThemeDAO {

    String ID_TABLE = "id";
    String NAME_TABLE = "name";
    String DESC_TABLE = "desc";
    String PRICE_TABLE = "price";

    RowMapper<Theme> rowMapper = (resultSet, rowNum) -> new Theme(
            resultSet.getLong(ID_TABLE),
            resultSet.getString(NAME_TABLE),
            resultSet.getString(DESC_TABLE),
            resultSet.getInt(PRICE_TABLE));
    RowMapper<Boolean> existRowMapper = (resultSet, rowNum) -> resultSet.getBoolean(
            "result");

    Boolean exist(Theme theme);
    Boolean existId(long id);
    Long create(Theme theme);
    List<Theme> list();
    void remove(long l);
    Theme find(long l);
}
