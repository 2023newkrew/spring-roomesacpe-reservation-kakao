package roomescape.repository.DBMapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class H2Mapper implements DatabaseMapper{
    public Long getAutoKey(KeyHolder keyHolder){
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public RowMapper<Reservation> reservationRowMapper(Long reservationId) {
        return (rs, rowNum) -> {
            LocalDate date = LocalDate.parse(rs.getString("date" ));
            LocalTime time = LocalTime.parse(rs.getString("time" ));
            String name = rs.getString("name" );
            Long themeId = rs.getLong("theme_id" );
            return new Reservation(reservationId, date, time, name, themeId);
        };
    }

    public RowMapper<Theme> themeRowMapper(Long themeId) {
        return (rs, rowNum) -> {
            String name = rs.getString("name" );
            String desc = rs.getString("price" );
            int price = rs.getInt("price" );
            return new Theme(themeId, name, desc, price);
        };
    }
}
