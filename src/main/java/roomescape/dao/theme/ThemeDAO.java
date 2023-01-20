package roomescape.dao.theme;

import java.util.List;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import roomescape.dto.Theme;

public interface ThemeDAO {

    String ID_TABLE = "id";
    String NAME_TABLE = "name";
    String DESC_TABLE = "desc";
    String PRICE_TABLE = "price";

    ResultSetExtractor<Theme> themeResultSetExtractor = rs -> {
        if (!rs.next()) {
            return null;
        }
        return new Theme(
            rs.getLong(ID_TABLE),
            rs.getString(NAME_TABLE),
            rs.getString(DESC_TABLE),
            rs.getInt(PRICE_TABLE));
    };
    ResultSetExtractor<Boolean> existResultSetExtractor = rs -> {
        if (!rs.next()) {
            return null;
        }
        return rs.getBoolean("result");
    };
    RowMapper<Theme> themeRowMapper = (rs, rowNum) -> new Theme(
            rs.getLong(ID_TABLE),
            rs.getString(NAME_TABLE),
            rs.getString(DESC_TABLE),
            rs.getInt(PRICE_TABLE));

    Boolean exist(Theme theme);
    Boolean existId(long id);
    Long create(Theme theme);
    List<Theme> list();
    void remove(long l);
    Theme find(long l);
}
