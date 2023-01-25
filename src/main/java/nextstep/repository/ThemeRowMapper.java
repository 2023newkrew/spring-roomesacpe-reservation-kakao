package nextstep.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import nextstep.model.Theme;
import org.springframework.jdbc.core.RowMapper;

public class ThemeRowMapper implements RowMapper<Theme> {

    @Override
    public Theme mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String desc = rs.getString("desc");
        int price = rs.getInt("price");
        return new Theme(id, name, desc, price);
    }
}
