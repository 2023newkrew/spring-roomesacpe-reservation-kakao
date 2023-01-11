package nextstep.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Theme theme;

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
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

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public static Reservation from(ResultSet resultSet) throws SQLException {
        return new Reservation(resultSet.getLong("ID"),
                resultSet.getDate("DATE").toLocalDate(),
                resultSet.getTime("TIME").toLocalTime(),
                resultSet.getString("NAME"),
                new Theme(
                        resultSet.getString("theme_name"),
                        resultSet.getString("theme_desc"),
                        resultSet.getInt("theme_price")
                )
        );
    }
}
