package roomescape.dao.theme;

import java.util.List;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import roomescape.dto.Theme;

public interface ThemeDAO {

    String ID_COLUMN = "id";
    String NAME_COLUMN = "name";
    String DESC_COLUMN = "desc";
    String PRICE_COLUMN = "price";

    ResultSetExtractor<Theme> themeResultSetExtractor = rs -> {
        if (!rs.next()) {
            return null;
        }
        return new Theme(
            rs.getLong(ID_COLUMN),
            rs.getString(NAME_COLUMN),
            rs.getString(DESC_COLUMN),
            rs.getInt(PRICE_COLUMN));
    };
    ResultSetExtractor<Boolean> existResultSetExtractor = rs -> {
        if (!rs.next()) {
            return null;
        }
        return rs.getBoolean("result");
    };
    RowMapper<Theme> themeRowMapper = (rs, rowNum) -> new Theme(
            rs.getLong(ID_COLUMN),
            rs.getString(NAME_COLUMN),
            rs.getString(DESC_COLUMN),
            rs.getInt(PRICE_COLUMN));

    Boolean exist(Theme theme);
    Boolean existId(long id);
    Long create(Theme theme);
    List<Theme> list();
    void remove(long l);
    Theme find(long l);
}
