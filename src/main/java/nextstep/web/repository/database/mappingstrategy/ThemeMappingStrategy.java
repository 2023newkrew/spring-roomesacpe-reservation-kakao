package nextstep.web.repository.database.mappingstrategy;

import nextstep.domain.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThemeMappingStrategy implements RowMappingStrategy<Theme> {
    @Override
    public Theme map(ResultSet rs) throws SQLException {
        return Theme.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .desc(rs.getString("desc"))
                .price(rs.getInt("price"))
                .build();
    }
}
