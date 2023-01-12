package roomescape.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Builder;

public class Theme {

    private String name;
    private String desc;
    private Integer price;

    @Builder
    public Theme(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getPrice() {
        return price;
    }

    public static Theme from(ResultSet rs) throws SQLException {
        return Theme.builder()
                .name(rs.getString("theme_name"))
                .desc(rs.getString("theme_desc"))
                .price(rs.getInt("theme_price"))
                .build();
    }
}
