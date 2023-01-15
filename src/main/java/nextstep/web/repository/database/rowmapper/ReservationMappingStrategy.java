package nextstep.web.repository.database.rowmapper;

import nextstep.domain.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationMappingStrategy implements RowMappingStrategy<Reservation> {

    private final ThemeMappingStrategy themeMappingStrategy = new ThemeMappingStrategy();

    @Override
    public Reservation map(ResultSet rs) throws SQLException {
        return Reservation.builder()
                .id(rs.getLong("id"))
                .date(rs.getDate("date")
                        .toLocalDate())
                .time(rs.getTime("time")
                        .toLocalTime())
                .name(rs.getString("name"))
                .theme(themeMappingStrategy.map(rs))
                .build();
    }
}
