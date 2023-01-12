package roomescape.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;

public class Reservation {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Theme theme;

    @Builder
    public Reservation(Long id, LocalDate date, LocalTime time, String name, roomescape.domain.Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

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

    public Theme getTheme() {
        return theme;
    }

    public Map<String, Object> buildParams() {
        final Map<String, Object> params = new HashMap<>();
        params.put("date", this.date);
        params.put("time", this.time);
        params.put("name", this.name);
        params.put("theme_name", this.theme.getName());
        params.put("theme_desc", this.theme.getDesc());
        params.put("theme_price", this.theme.getPrice());

        return params;
    }

    public static Reservation from(ResultSet rs) throws SQLException {
        return Reservation.builder()
                .id(rs.getLong("id"))
                .date(rs.getDate("date").toLocalDate())
                .time(rs.getTime("time").toLocalTime())
                .name(rs.getString("name"))
                .theme(Theme.from(rs))
                .build();
    }
}
