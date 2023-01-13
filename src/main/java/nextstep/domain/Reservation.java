package nextstep.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nextstep.web.reservation.dto.CreateReservationRequestDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@AllArgsConstructor
public class Reservation {

    public static final String BASE_URL = "/reservations";

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;

    private Long themeId;

    private Reservation(LocalDate date, LocalTime time, String name, Long theme) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = theme;
    }

    public static Reservation of(LocalDate date, LocalTime time, String name, Long themeId) {
        return new Reservation(date, time, name, themeId);
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

    public static Reservation from(CreateReservationRequestDto requestDto){
        return Reservation.builder()
                .date(requestDto.getDate())
                .time(requestDto.getTime())
                .name(requestDto.getName())
                .themeId(requestDto.getThemeId())
                .build();
    }
}
