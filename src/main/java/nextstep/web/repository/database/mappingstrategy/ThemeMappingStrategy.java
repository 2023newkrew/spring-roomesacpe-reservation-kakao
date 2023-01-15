package nextstep.web.repository.database.mappingstrategy;

import nextstep.domain.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThemeMappingStrategy implements RowMappingStrategy<Theme> {
    @Override
    public Theme map(ResultSet rs) throws SQLException {
        return Theme.builder()
                .name(rs.getString("theme_name"))
                .desc(rs.getString("theme_desc"))
                .price(rs.getInt("theme_price"))
                .build();
    }
}
