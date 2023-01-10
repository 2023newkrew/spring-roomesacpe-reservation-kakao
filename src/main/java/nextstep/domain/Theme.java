package nextstep.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Builder
@Getter
@AllArgsConstructor
public class Theme {
    private String name;
    private String desc;
    private Integer price;

    public static Theme from(ResultSet rs) throws SQLException {
        return Theme.builder()
                .name(rs.getString("theme_name"))
                .desc(rs.getString("theme_desc"))
                .price(rs.getInt("theme_price"))
                .build();
    }
}
