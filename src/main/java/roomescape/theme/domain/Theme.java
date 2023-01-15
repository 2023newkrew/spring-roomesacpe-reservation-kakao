package roomescape.theme.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class Theme {

    private Long id;

    private final String name;

    private final String desc;

    private final Integer price;

    public void setId(final Long id) {
        this.id = id;
    }

    public static Theme from(ResultSet rs) throws SQLException {
        return Theme.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .desc(rs.getString("desc"))
                .price(rs.getInt("price"))
                .build();
    }
}
