package nextstep.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static Theme from(ResultSet resultSet) throws SQLException {
        return new Theme(resultSet.getString("name"),
                resultSet.getString("desc"),
                resultSet.getInt("price"));
    }
}
