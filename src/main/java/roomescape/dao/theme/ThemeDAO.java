package roomescape.dao.theme;

import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import roomescape.dto.Theme;
import roomescape.exception.BadRequestException;

public abstract class ThemeDAO {

    private static final String ID_TABLE = "id";
    private static final String NAME_TABLE = "name";
    private static final String DESC_TABLE = "desc";
    private static final String PRICE_TABLE = "price";

    private static final RowMapper<Theme> rowMapper = (resultSet, rowNum) -> new Theme(
            resultSet.getLong(ID_TABLE),
            resultSet.getString(NAME_TABLE),
            resultSet.getString(DESC_TABLE),
            resultSet.getInt(PRICE_TABLE));
    private static final RowMapper<Boolean> existRowMapper = (resultSet, rowNum) -> resultSet.getBoolean(
            "result");

    protected void validate(Theme theme) {
        if (theme.getName() == null || theme.getDesc() == null || theme.getPrice() == null) {
            throw new BadRequestException();
        }
        if (exist(theme)) {
            throw new BadRequestException();
        }
    }

    protected static RowMapper<Theme> getRowMapper() {
        return rowMapper;
    }

    protected static RowMapper<Boolean> getExistRowMapper() {
        return existRowMapper;
    }

    public abstract boolean exist(Theme theme);

    public abstract Long create(Theme theme);

    public abstract List<Theme> list();

    public abstract void remove(Long l);
}
