package roomescape.theme.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import roomescape.theme.entity.Theme;

public class ThemeRowMapper implements RowMapper<Theme> {

    @Override
    public Theme mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return Theme.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .desc(rs.getString("desc"))
                .price(rs.getInt("price"))
                .build();
    }
}
