package nextstep.web.repository.database.mappingstrategy;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationMappingStrategy implements RowMappingStrategy<Reservation> {

    @Override
    public Reservation map(ResultSet rs) throws SQLException {
        return Reservation.builder()
                .id(rs.getLong("id"))
                .date(rs.getDate("date")
                        .toLocalDate())
                .time(rs.getTime("time")
                        .toLocalTime())
                .name(rs.getString("name"))
                .theme(
                        Theme.builder()
                                .id(rs.getLong("theme_id"))
                                .name(rs.getString("theme.name"))
                                .desc(rs.getString("theme.desc"))
                                .price(rs.getInt("theme.price"))
                                .build()
                )
                .build();
    }
}
