package nextstep.main.java.nextstep.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@RequiredArgsConstructor
public class Reservation {
    private Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Long themeId;

    public Reservation(Long id, Reservation reservation) {
        this(id, reservation.getDate(), reservation.getTime(), reservation.getName(), reservation.getThemeId());
    }

    public static Reservation of(ResultSet resultSet) throws SQLException {
        return new Reservation(resultSet.getLong("id"),
                resultSet.getDate("date")
                        .toLocalDate(),
                resultSet.getTime("time")
                        .toLocalTime(),
                resultSet.getString("name"),
                resultSet.getLong("theme_id")
        );
    }
}
