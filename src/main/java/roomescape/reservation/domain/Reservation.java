package roomescape.reservation.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reservation {

    private Long id;

    private LocalDate date;

    private LocalTime time;

    private String name;

    private Long themeId;

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Long getThemeId() {
        return themeId;
    }

    public static Reservation from(ResultSet rs) throws SQLException {
        return Reservation.builder()
                .id(rs.getLong("id"))
                .date(rs.getDate("date").toLocalDate())
                .time(rs.getTime("time").toLocalTime())
                .name(rs.getString("name"))
                .themeId(rs.getLong("theme_id"))
                .build();
    }
}
