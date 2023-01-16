package nextstep.main.java.nextstep.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class Theme {
    private final String name;
    private final String desc;
    private final Integer price;
    private Long id;

    public Theme(Long id, Theme theme) {
        this(id, theme.getName(), theme.getDesc(), theme.getPrice());
    }

    public static Theme of(ResultSet resultSet) throws SQLException {
        return new Theme((resultSet.getLong("id")),
                resultSet.getString("name"),
                resultSet.getString("desc"),
                resultSet.getInt("price"));
    }
}
