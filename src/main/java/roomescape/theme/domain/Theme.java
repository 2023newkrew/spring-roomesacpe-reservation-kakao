package roomescape.theme.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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

    public Map<String, Object> buildParams() {
        final Map<String, Object> params = new HashMap<>();
        params.put("name", this.name);
        params.put("desc", this.desc);
        params.put("price", this.price);

        return params;
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
