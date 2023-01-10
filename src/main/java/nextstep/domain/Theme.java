package nextstep.domain;

import lombok.Builder;

import java.sql.ResultSet;
import java.sql.SQLException;

@Builder
public class Theme {
    private String name;
    private String desc;
    private Integer price;

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
        if (!rs.next()) {
            throw new RuntimeException();
        }
        return Theme.builder()
                .name(rs.getString("theme_name"))
                .desc(rs.getString("theme_desc"))
                .price(rs.getInt("theme_price"))
                .build();
    }
}
